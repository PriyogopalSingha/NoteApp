package com.dullgames.notesapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dullgames.notesapp.Models.Note

class NotesAdapter(private val context: Context, val listener: NoteClickListener):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_item_layout,parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList.get(position)
        holder.title.text = currentNote.title
        holder.desc.text = currentNote.note
        holder.date.text = currentNote.date
        holder.container.setCardBackgroundColor(holder.itemView.resources.getColor(R.color.yellow))

        holder.container.setOnClickListener{
            listener.onNoteClicked(notesList[holder.adapterPosition])
        }
        holder.container.setOnLongClickListener{
            listener.onNoteLongClicked(notesList[holder.adapterPosition], holder.container)
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun searchNote(search: String){
        notesList.clear()

        for(item in fullList){
            if(item?.title?.lowercase()?.contains(search.lowercase()) == true || item.note?.lowercase()?.contains(search.lowercase()) == true){
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val container = itemView.findViewById<CardView>(R.id.card_container)
        val title = itemView.findViewById<TextView>(R.id.note_title)
        val desc = itemView.findViewById<TextView>(R.id.note_desc)
        val date = itemView.findViewById<TextView>(R.id.note_date)
    }

    interface NoteClickListener{
        fun onNoteClicked(note: Note)
        fun onNoteLongClicked(note: Note, cardView: CardView)
    }

}