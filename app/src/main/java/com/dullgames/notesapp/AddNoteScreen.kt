package com.dullgames.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dullgames.notesapp.Models.Note
import com.dullgames.notesapp.databinding.ActivityAddNoteScreenBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteScreen : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteScreenBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try{
            oldNote = intent.getSerializableExtra("selected_note") as Note
            binding.noteTitleEdittext.setText(oldNote.title)
            binding.noteBodyEdittext.setText(oldNote.note)
            isUpdated = true
        }catch (e: Exception){
            e.printStackTrace()
        }
        binding.saveNote.setOnClickListener {
            val note_title = binding.noteTitleEdittext.text.toString()
            val note_desc = binding.noteBodyEdittext.text.toString()
            if(note_title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("dd-M-yyyy    hh:mm:ss")
                if(isUpdated){
                    note = Note(
                        oldNote.id, note_title, note_desc, formatter.format(Date())
                    )
                }else{
                    note = Note(
                        null, note_title, note_desc, formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this@AddNoteScreen, "PLease enter some text", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}