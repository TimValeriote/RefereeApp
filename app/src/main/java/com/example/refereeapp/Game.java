package com.example.refereeapp;
import com.google.firebase.Timestamp;

import java.util.Date;

public class Game {
    private Date dateTime;
    private String location;
    private String league;
    private int pay;
    private String position;
    private String ref2;
    private String ref3;
    private String ref4;

    public Game(String gameLocation,String gameLeague,int gamePay,String gamePosition,String gameRef2,String gameRef3,String gameRef4){
        this.location = gameLocation;
        this.league = gameLeague;
        this.pay = gamePay;
        this.position = gamePosition;
        this.ref2 = gameRef2;
        this.ref3 = gameRef3;
        this.ref4 = gameRef4;
    }

    public Game(){}

    public void setDateTime(Date inputDate){this.dateTime = inputDate; }
    public Date getDate(){
        return dateTime;
    }
    public String getLocation(){
        return location;
    }
    public String getLeague(){
        return league;
    }
    public int getPay(){
        return pay;
    }
    public String getPosition(){
        return position;
    }
    public String getRef2(){
        return ref2;
    }
    public String getRef3(){
        return ref3;
    }
    public String getRef4(){
        return ref4;
    }
}
