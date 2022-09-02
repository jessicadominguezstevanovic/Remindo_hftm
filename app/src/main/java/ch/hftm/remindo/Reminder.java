package ch.hftm.remindo;

import com.google.firebase.Timestamp;

public class Reminder {
    String userUID;
    String title;
    String notes;
    String date;
    Timestamp timestamp;

    public Reminder(){

    }

    public Reminder(String userUID, String title, String notes, String date, Timestamp timestamp) {
        this.userUID = userUID;
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.timestamp = timestamp;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
