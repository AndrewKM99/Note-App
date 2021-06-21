package com.example.notetakerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Note> noteListView;
    NoteDatabase noteDatabase;
    TextView noItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.notesList);
        noteDatabase = new NoteDatabase(this);
        List<Note> allNotes = noteDatabase.getNotes();
        noItemText = findViewById(R.id.noItemText);

        if (allNotes.isEmpty())
        {
            noItemText.setVisibility(View.VISIBLE);
        }
        else
            {
            noItemText.setVisibility(View.GONE);
            displayList(allNotes);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addButton)
        {
            Intent i = new Intent(this, AddNotes.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
    //displays the list of notes that the user has saved on the database

    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, allNotes);
        recyclerView.setAdapter(adapter);
    }



    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = noteDatabase.getNotes();
        if (getAllNotes.isEmpty()) {
            noItemText.setVisibility(View.VISIBLE);
        } else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }
    }

}