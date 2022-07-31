package com.example.noteapp.presenter.note.list

import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.domain.note.util.NoteOrder
import com.example.noteapp.domain.note.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)
