package com.example.noteapp.domain.note

import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.domain.note.util.NoteOrder
import com.example.noteapp.domain.note.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { list ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> list.sortedBy { it.color }
                        is NoteOrder.Date -> list.sortedBy { it.date }
                        is NoteOrder.Title -> list.sortedBy { it.title.toLowerCase(Locale.ROOT) }
                    }
                }
                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> list.sortedByDescending { it.color }
                        is NoteOrder.Date -> list.sortedByDescending { it.date }
                        is NoteOrder.Title -> list.sortedByDescending { it.title.lowercase(Locale.ROOT) }
                    }
                }
            }
        }
    }
}