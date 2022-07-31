package com.example.noteapp.domain.note

import com.example.noteapp.domain.note.model.InvalidNoteException
import com.example.noteapp.domain.note.model.Note

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Note title can't be null. Error Code:1")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Note content can't be null. Error Code:2")
        }
        repository.insertNote(note)
    }
}