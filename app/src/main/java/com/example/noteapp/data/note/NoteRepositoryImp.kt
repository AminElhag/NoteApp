package com.example.noteapp.data.note

import com.example.noteapp.domain.note.NoteRepository
import com.example.noteapp.domain.note.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImp constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }
}