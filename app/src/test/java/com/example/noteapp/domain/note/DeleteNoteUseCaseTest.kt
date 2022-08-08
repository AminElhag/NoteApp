package com.example.noteapp.domain.note

import com.example.noteapp.data.note.NoteRepositoryImpTest
import com.example.noteapp.domain.note.model.Note
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {
    private lateinit var repository: NoteRepositoryImpTest
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setup() {
        repository = NoteRepositoryImpTest()
        deleteNoteUseCase = DeleteNoteUseCase(repository)
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
    fun `delete note, Correct`() = runBlocking {
        val note = Note(1, "b", "b", 1, 1L)
        deleteNoteUseCase(note)
        Truth.assertThat(getNoteByIdUseCase(1)).isNull()
    }
}