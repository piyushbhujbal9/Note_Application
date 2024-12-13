package com.firstproject.gaeinmemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.firstproject.gaeinmemo.database.database
import com.firstproject.gaeinmemo.repository.NoteRepository
import com.firstproject.gaeinmemo.viewmodel.NoteViewModel
import com.firstproject.gaeinmemo.viewmodel.NoteViewModelFactory
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setUpViewModel()
    }
    private fun setUpViewModel(){
        val noteRepository= NoteRepository(database(this))
        val noteViewModelFactory= NoteViewModelFactory(application,noteRepository)
        noteViewModel= ViewModelProvider(this,noteViewModelFactory).get(NoteViewModel::class.java)
    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val searchView = menu?.findItem(R.id.search_item)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)

        return true
    }*/


}