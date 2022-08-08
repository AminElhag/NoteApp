package com.example.noteapp.domain.note

import com.example.noteapp.data.note.NoteRepositoryImpTest
import com.example.noteapp.domain.note.model.InvalidNoteException
import com.example.noteapp.domain.note.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertNoteUseCaseTest {
    private lateinit var repository: NoteRepositoryImpTest
    private lateinit var insertNoteUseCase: InsertNoteUseCase
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setup() {
        repository = NoteRepositoryImpTest()
        insertNoteUseCase = InsertNoteUseCase(repository)
        getNoteByIdUseCase = GetNoteByIdUseCase(repository)
    }

    @Test
    fun `insert note, Correct`() = runBlocking {
        val note = Note(1, "b", "b", 1, 1L)
        insertNoteUseCase(note)
        assertThat(getNoteByIdUseCase(1)).isNotNull()
    }

    @Test
    fun `insert note without title, Throw title exception`() = runBlocking {
        val note = Note(1, "", "b", 1, 1L)
        try {
            insertNoteUseCase(note)
        } catch (e: Exception) {
            assertThat(e.message).contains("Note title can't be null. Error Code:1")
        }
    }

    @Test
    fun `insert note without content, Throw content exception`() = runBlocking {
        val note = Note(1, "b", "", 1, 1L)
        try {
            insertNoteUseCase(note)
        } catch (e: Exception) {
            assertThat(e.message).contains("Note content can't be null. Error Code:2")
        }
    }
}