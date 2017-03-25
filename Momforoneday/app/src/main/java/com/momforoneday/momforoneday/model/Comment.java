package com.momforoneday.momforoneday.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabrielguimo on 25/03/17.
 */

public class Comment {

    private String owner;
    private String date;
    private String comment;

    public Comment(String owner, String comment) {
        this.owner = owner;
        this.comment = comment;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this.date = dateString + " às " + hourString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this.date = dateString + " às " + hourString;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
