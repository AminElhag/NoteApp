package com.example.noteapp.presenter.note.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.note.NoteUseCases
import com.example.noteapp.domain.note.model.InvalidNoteException
import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.domain.note.util.NoteOrder
import com.example.noteapp.domain.note.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var jop: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(noteEvent: NoteEvent) {
        when (noteEvent) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(noteEvent.note)
                    recentlyDeletedNote = noteEvent.note
                }
            }
            is NoteEvent.Order -> {
                if (state.value.noteOrder::class == noteEvent.noteOrder::class
                    && state.value.noteOrder.orderType == noteEvent.noteOrder.orderType
                ) {
                    return
                }
                getNotes(noteEvent.noteOrder)
            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let { note ->
                        try {
                            noteUseCases.insertNoteUseCase(note)
                            recentlyDeletedNote = null
                        } catch (e: InvalidNoteException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        jop?.cancel()
        jop = noteUseCases.getNotesUseCase(noteOrder).onEach { list ->
            _state.value = state.value.copy(
                notes = list,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}