package com.example.noteapppart2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteClickListener{

    private List<Note> tempNoteList;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton addNoteButton;

    private Note editingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.noteRecyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);

        tempNoteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(tempNoteList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);

        addNoteButton.setOnClickListener(v -> {
            showNoteDialog(null);
        });


    }

    @Override
    public void onNoteClick(Note note) {
        showNoteDialog(note);
    }

    private void showNoteDialog(Note note) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_note, null);
        dialogBuilder.setView(dialogView);

        final EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        final EditText bodyEditText = dialogView.findViewById(R.id.bodyEditText);

        if (note != null) {
            titleEditText.setText(note.getTitle());
            bodyEditText.setText(note.getBody());
            editingNote = note;
        } else {
            editingNote = null;
        }

        dialogBuilder.setTitle(note != null ? "Edit Note" : "Add Note");
        dialogBuilder.setPositiveButton("Save", (dialog, whichButton) -> {
            String title = titleEditText.getText().toString().trim();
            String body = bodyEditText.getText().toString().trim();

            if (editingNote != null) {
                editingNote.setTitle(title);
                editingNote.setBody(body);
                noteAdapter.notifyDataSetChanged();
            } else {
                Note newNote = new Note(title, body);
                tempNoteList.add(newNote);
                noteAdapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}
