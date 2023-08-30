package com.example.noteapppart2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements NoteClickListener {

    private List<Note> noteList;
    private NoteClickListener noteClickListener;

    public NoteAdapter(List<Note> noteList, NoteClickListener noteClickListener) {
        this.noteList = noteList;
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.bodyTextView.setText(note.getBody());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public void onNoteClick(Note note) {
    }

    public interface NoteClickListener {
        void onNoteClick(Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, bodyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Note clickedNote = noteList.get(position);
                noteClickListener.onNoteClick(clickedNote);
            }
        }
    }
}
