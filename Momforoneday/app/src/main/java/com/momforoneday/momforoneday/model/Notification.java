package com.momforoneday.momforoneday.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Notification {

    private String text;
    private String date;
    private Caregiver sender;
    private User receiver;

    public Notification(){}

    public Notification(String _text, Caregiver _sender, User _receiver) {
        this.text = _text;
        this.sender = _sender;
        this.receiver = _receiver;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this.date = dateString + " às " + hourString;
    }

    public String getText() {
        return text;
    }

    public void setText(String _text) {
        this.text = _text;
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

    public Caregiver getSender() {
        return sender;
    }

    public void setSender(Caregiver _sender) {
        this.sender = _sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User _receiver) {
        this.receiver = _receiver;
    }
}
