package com.example.noteapp.di.note

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.note.NoteDatabase
import com.example.noteapp.data.note.NoteRepositoryImp
import com.example.noteapp.domain.note.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {
    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app, NoteDatabase::class.java
    ).build()

    @Singleton
    @Provides
    fun provideNoteRepository(database: NoteDatabase): NoteRepository = NoteRepositoryImp(
        database.noteDao
    )

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
    }
}