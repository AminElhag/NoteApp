package com.example.noteapp.domain.note

import com.example.noteapp.domain.note.model.Note

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}