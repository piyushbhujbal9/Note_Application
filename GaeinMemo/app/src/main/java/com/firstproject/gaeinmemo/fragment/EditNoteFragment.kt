package com.firstproject.gaeinmemo.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.firstproject.gaeinmemo.MainActivity
import com.firstproject.gaeinmemo.R
import com.firstproject.gaeinmemo.databinding.FragmentEditNoteBinding
import com.firstproject.gaeinmemo.model.Note
import com.firstproject.gaeinmemo.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {
    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val args: EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding=FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote=args.note!!
        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.description)
        binding.editNoteFab.setOnClickListener{
            val title=binding.editNoteTitle.text.toString().trim()
            val description=binding.editNoteDesc.text.toString().trim()
            if(title.isNotEmpty()){
                val note= Note(currentNote.id,title,description)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }
            else{
                Toast.makeText(view.context,"Please Enter Note Title !", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deleteNote(context: Context){
       AlertDialog.Builder(context).apply {
           setTitle("Delete Note")
           setMessage("Are You Sure?")
           setPositiveButton("Delete"){_,_->
               notesViewModel.deleteNote(currentNote)
               Toast.makeText(context,"Note Deleted Successfully !",Toast.LENGTH_SHORT).show()
               view?.findNavController()?.popBackStack(R.id.homeFragment,false)
           }
           setNegativeButton("Cancel",null)
       }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return when(menuItem.itemId){
           R.id.deleteMenu->{
               deleteNote(requireContext())
               true
           }
           else->false
       }
    }
    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }


}