package com.example.refereeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    private ArrayList<Game> gamesList;
    Context context;

    public myAdapter(Context ct,ArrayList<Game> games){
        this.gamesList = games;
        this.context = ct;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_games_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Game game = gamesList.get(position);
        String tempDate = converDateToString(game.getDate(),true);
        String tempTime = converDateToString(game.getDate(),false);
        String pay = String.valueOf(game.getPay());
        holder.setDate(tempDate);
        holder.setTime(tempTime);
        holder.setLeague(game.getLeague());
        holder.setPay(pay);
        holder.setLocation(game.getLocation());
        holder.setOtherRefs(game.getAllRefs());
        holder.setPosition(game.getPosition());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,location,league,position,pay,otherRefs;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.descDate);
            time = itemView.findViewById(R.id.descTime);
            location = itemView.findViewById(R.id.descLocation);
            league = itemView.findViewById(R.id.descLeague);
            position = itemView.findViewById(R.id.descPosition);
            pay = itemView.findViewById(R.id.descPay);
            otherRefs = itemView.findViewById(R.id.descOtherRefs);

        }

        public void setDate(String input){
            date.setText(input);
        }
        public void setTime(String input){
            time.setText(input);
        }
        public void setLocation(String input){
            location.setText(input);
        }
        public void setLeague(String input){
            league.setText(input);
        }
        public void setPosition(String input){
            position.setText(input);
        }
        public void setPay(String input){
            pay.setText(input);
        }
        public void setOtherRefs(String input){
            otherRefs.setText(input);
        }

    }

    public String converDateToString (Date date, boolean dateOrTime){
        String temp;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        if (dateOrTime == true){
            temp = dateFormat.format(date);
        } else {
            temp = timeFormat.format(date);
        }
        return temp;
    }
}
