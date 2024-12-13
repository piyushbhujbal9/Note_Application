package com.firstproject.gaeinmemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.firstproject.gaeinmemo.model.Note
import java.util.concurrent.locks.Lock

@Database(entities = [Note::class], version = 1)
abstract class database:RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    companion object{
        @Volatile
        private var instance:database?=null
        private val Lock=Any()
        operator fun invoke(context: Context)= instance?:
        synchronized(Lock){
            instance?:
            createDatabase(context).also{
                instance=it

            }

        }
        private fun createDatabase(context: Context)=
            androidx.room.Room.databaseBuilder(
                context.applicationContext,
                database::class.java,
                "note_database"
            ).build()
    }
}