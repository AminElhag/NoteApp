package com.example.noteapp.domain.note

import com.example.noteapp.domain.note.model.Note

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}