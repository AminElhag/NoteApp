package com.example.noteapp.domain.note

import com.example.noteapp.data.note.NoteRepositoryImpTest
import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.domain.note.util.NoteOrder
import com.example.noteapp.domain.note.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var testRepositoryImpTest: NoteRepositoryImpTest

    @Before
    fun setup() {
        testRepositoryImpTest = NoteRepositoryImpTest()
        getNotesUseCase = GetNotesUseCase(testRepositoryImpTest)

        val noteList = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            noteList.add(
                Note(index, c.toString(), c.toString(), index, index.toLong())
            )
        }
        noteList.shuffle()
        noteList.forEach { note ->
            runBlocking {
                testRepositoryImpTest.insertNote(note)
            }
        }
    }

    @Test
    fun `get note by title ascending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get note by color ascending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `get note by Date ascending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].date).isLessThan(notes[i + 1].date)
        }
    }

    @Test
    fun `get note by title descending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get note by color descending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }

    @Test
    fun `get note by Date descending, Correct`() = runBlocking {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].date).isGreaterThan(notes[i + 1].date)
        }
    }
}