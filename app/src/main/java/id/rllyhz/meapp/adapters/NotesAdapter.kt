package id.rllyhz.meapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.databinding.ItemNotesBinding
import id.rllyhz.meapp.utils.ColorHelper
import id.rllyhz.meapp.utils.formattedUpdatedAt

class NotesAdapter : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    // viewholder
    inner class NotesViewHolder(
        private val binding: ItemNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvItemNotesTitle.text = note.title
                tvItemNotesContent.text = note.content
                tvItemNotesCreatedAt.text = note.formattedUpdatedAt()
                cvItemNotes.setCardBackgroundColor(ColorHelper.getRandomColor(itemView.context))

                itemView.setOnClickListener {
                    callback?.onNoteClick(note)
                }
            }
        }
    }

    // Diffutil for handling comparison of items
    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem

    }

    // for item click listener
    interface NoteItemClickCallback {
        fun onNoteClick(note: Note)
    }

    private var callback: NoteItemClickCallback? = null

    fun setOnItemClickListener(listener: NoteItemClickCallback) {
        callback = listener
    }
}