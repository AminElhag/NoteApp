package com.example.noteapp.domain.note

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
)
