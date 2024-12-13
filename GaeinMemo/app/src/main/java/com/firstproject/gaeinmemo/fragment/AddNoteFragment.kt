package com.firstproject.gaeinmemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.firstproject.gaeinmemo.MainActivity
import com.firstproject.gaeinmemo.R
import com.firstproject.gaeinmemo.databinding.FragmentAddNoteBinding
import com.firstproject.gaeinmemo.model.Note
import com.firstproject.gaeinmemo.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {
    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
       addNoteBinding=FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view
    }

    private fun saveNote(view: View){
        val noteTitle=binding.addNoteTitle.text.toString().trim()
        val noteBody=binding.addNoteDesc.text.toString().trim()
        if(noteTitle.isEmpty()){
            Toast.makeText(view.context,"Please Enter Note Title !",Toast.LENGTH_SHORT).show()
        }
        else{
            val note= Note(1,noteTitle,noteBody)
            notesViewModel.addNote(note)
            Toast.makeText(view.context,"Saved Successfully !",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveNote(addNoteView)
                true
            }
            else->false
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }


}