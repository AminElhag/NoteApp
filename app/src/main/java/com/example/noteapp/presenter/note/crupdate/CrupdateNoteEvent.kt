package com.example.noteapp.presenter.note.crupdate

import androidx.compose.ui.focus.FocusState

sealed class CrupdateNoteEvent {
    data class EnteredTitle(val value: String) : CrupdateNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : CrupdateNoteEvent()
    data class EnteredContent(val value: String) : CrupdateNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : CrupdateNoteEvent()
    data class ChangeColor(val color: Int) : CrupdateNoteEvent()
    object SaveNote : CrupdateNoteEvent()
}