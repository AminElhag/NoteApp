package com.example.noteapp.domain.note

import com.example.noteapp.data.note.NoteRepositoryImpTest
import com.example.noteapp.domain.note.model.Note
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteByIdUseCaseTest {

    private lateinit var repository: NoteRepositoryImpTest
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setup() {
        repository = NoteRepositoryImpTest()
        getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        val noteList = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            noteList.add(
                Note(index, c.toString(), c.toString(), index, index.toLong())
            )
        }
        noteList.shuffle()
        noteList.forEach { note ->
            runBlocking {
                repository.insertNote(note)
            }
        }
    }

    @Test
    fun `get note by id, Correct`() = runBlocking {
        ('a'..'z').forEachIndexed { index, c ->
            val note = getNoteByIdUseCase(index)
            Truth.assertThat(note?.title).isEqualTo(c.toString())
        }
    }

    @Test
    fun `get note by id, Null`() = runBlocking {
        val note = getNoteByIdUseCase(100)
        Truth.assertThat(note?.title).isNull()
    }
}