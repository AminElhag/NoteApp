package com.example.noteapp.domain.note.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
