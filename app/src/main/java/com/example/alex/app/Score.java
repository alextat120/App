package com.example.alex.app;

/**
 * Created by Alex on 4/12/2015.
 */
public class Score {
    int _id;
    String _name;
    String _time;

    public Score(int id, String name, String time) {
        this._id = id;
        this._time = time;
        this._name = name;
    }

    public Score(String name, String time) {
        this._name = name;
        this._time = time;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting time
    public String getTime(){
        return this._time;
    }


}