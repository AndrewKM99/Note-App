package com.example.notetakerapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

private LayoutInflater inflater;
private List<Note> noteList;

Adapter(Context context, List<Note> noteList)
{
    this.inflater = LayoutInflater.from(context);
    this.noteList = noteList;
}

    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder viewHolder, int i) {

    String title = noteList.get(i).getTitle();
    String date = noteList.get(i).getDate();
    String time = noteList.get(i).getTime();
    long id = noteList.get(i).getID();

    Log.d("Test1234:", "Title: " + title + " " + date + " " + time + " " + id);

    viewHolder.nTitle.setText(title);
    viewHolder.nDates.setText(date);
    viewHolder.nTime.setText(time);
    viewHolder.nID.setText(String.valueOf(noteList.get(i).getID()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nID, nTitle, nDates, nTime;

        public ViewHolder(View itemView) {
            super(itemView);
            nID = itemView.findViewById(R.id.listID);
            nTitle = itemView.findViewById(R.id.textName);
            nDates = itemView.findViewById(R.id.textDate);
            nTime = itemView.findViewById((R.id.textTime));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),NoteDetails.class);
                    i.putExtra("ID",noteList.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(i);
                }
            });
        }


    }
}
