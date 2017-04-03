package com.momforoneday.momforoneday.model;

import android.media.Image;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Notification {

    private String text;
    private String date;
    private String sender;
    private User receiver;
    private String image;
    public Notification(){}

    public Notification(String text, String sender, User receiver) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this.date = dateString + " às " + hourString;
    }

    public String getImage() { return image;}
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setImage(String image){ this.image = image;}
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
