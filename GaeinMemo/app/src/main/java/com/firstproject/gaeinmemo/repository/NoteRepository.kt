package com.firstproject.gaeinmemo.repository

import com.firstproject.gaeinmemo.database.database
import com.firstproject.gaeinmemo.model.Note

class NoteRepository(private val db:database) {
    suspend fun insertNote(note: Note)=db.getNoteDao().insertNote(note)
    suspend fun updateNote(note: Note)=db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNote(note)
    fun getAllNotes()=db.getNoteDao().getAllNotes()
    fun searchNote(query:String?)=db.getNoteDao().searchNote(query)


}