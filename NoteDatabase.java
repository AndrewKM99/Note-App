package com.example.notetakerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "notedb";
    private static final String DATABASE_TABLE = "tabledb";

    private static final String KEY_ID = "ID";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    NoteDatabase(Context context)
    {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String query = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID +" INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT, " + KEY_CONTENT + " TEXT, " + KEY_DATE + " TEXT, " + KEY_TIME + " TEXT" + ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        if(oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    //adds note to the database, which in turn is displayed in the notes list on the app

    public long addNote(Note note)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentV = new ContentValues();
        contentV.put(KEY_TITLE, note.getTitle());
        contentV.put(KEY_CONTENT, note.getContent());
        contentV.put(KEY_DATE, note.getDate());
        contentV.put(KEY_TIME, note.getTime());

        long ID = db.insert(DATABASE_TABLE, null, contentV);
        Log.d("Added note", "ID Number:" + ID);

        return ID;

    }

    //retrieves the note from th database

    public Note getNote(long ID)
    {


        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[]
                { KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor =  db.query(DATABASE_TABLE, query,KEY_ID + "=?", new String[]{String.valueOf(ID)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        Log.d("CursorTest", "Cursor:" + cursor.moveToFirst());



        return new Note(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );

    }

//allows user to edit notes

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentV = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getID());
        contentV.put(KEY_TITLE,note.getTitle());
        contentV.put(KEY_CONTENT,note.getContent());
        contentV.put(KEY_DATE,note.getDate());
        contentV.put(KEY_TIME,note.getTime());
        return db.update(DATABASE_TABLE,contentV,KEY_ID + "=?",new String[]{String.valueOf(note.getID())});
    }

    //allows user to delete note
    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    //displays a list of all of the notes - ID, Title, content, date and time.

    public List<Note> getNotes(){
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE +" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }

        return allNotes;

    }

}
