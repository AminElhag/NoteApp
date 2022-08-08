package com.example.noteapp.di

import android.app.Activity
import android.content.Intent
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class NoteAppTest : AndroidJUnitRunner() {
    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        return super.newActivity(cl, HiltTestApplication::class.java.name, intent)
    }
}