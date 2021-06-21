package com.example.notetakerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddNotes extends AppCompatActivity {

    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar calandr;
    String currentDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Adding a New Note");

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() != 0);
                getSupportActionBar().setTitle(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        calandr = Calendar.getInstance();
        currentDate = calandr.get(Calendar.YEAR) + "/" + calandr.get(Calendar.MONTH) + "/" + calandr.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(calandr.get(Calendar.HOUR)) + ":" + pad(calandr.get(Calendar.MINUTE)) + ":" + pad(calandr.get(Calendar.SECOND));

        Log.d("calendar", "Date and Time" + currentDate + "and" + currentTime);
    }

    //pads the time by adding a 0 to it if it is a single figure number <10

    private String pad(int i)
    {
        if(i<10)
            return "0" + i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_notes_menu, menu);
        return true;
    }

    //either saves or cancels the note that you are writing depending on which button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save)



        {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), currentDate, currentTime);
            NoteDatabase db = new NoteDatabase(this);
            long id = db.addNote(note);
            Note check = db.getNote(id);
            Log.d("inserted", "Note: "+ id + " -> Title:" + check.getTitle()+" Date: "+ check.getDate());
            onBackPressed();

        }
        else if(item.getItemId() == R.id.delete) {

            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
