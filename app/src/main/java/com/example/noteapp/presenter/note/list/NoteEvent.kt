package com.example.noteapp.presenter.note.list

import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.domain.note.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    object RestoreNote : NoteEvent()
    object ToggleOrderSection : NoteEvent()
}
