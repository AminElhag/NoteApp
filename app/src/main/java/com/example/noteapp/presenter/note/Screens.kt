package com.example.noteapp.presenter.note

sealed class Screens(val route: String) {
    object NoteList : Screens("note_list_screen")
    object CrupdateList : Screens("note_create_update_screen")
}
