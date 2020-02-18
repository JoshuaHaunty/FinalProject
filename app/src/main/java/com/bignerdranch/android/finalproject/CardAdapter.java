package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 4/23/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    List<DataModel> studentNames;
    private Context context;
    static List<String> absentStudents = new ArrayList<>();
    static List<String> totalAbsentStudents = new ArrayList<>();


    public CardAdapter(List<DataModel> studentNames, Context context) {
        super();

        this.studentNames = studentNames;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        DataModel data = studentNames.get(position);
        holder.studentName.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return studentNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;
        //public Button bSubmit;
        public ToggleButton bToggle;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            bToggle = (ToggleButton) itemView.findViewById(R.id.bToggle);

            bToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        absentStudents.add(studentName.getText().toString());
                        totalAbsentStudents.add(studentName.getText().toString());
                        Toast.makeText(context, String.valueOf(studentName.getText()) + " is now absent", Toast.LENGTH_SHORT).show();
                    } else {
                        absentStudents.remove(studentName.getText().toString());
                        totalAbsentStudents.remove(studentName.getText().toString());
                    }

                }
            });

        }
    }


}
