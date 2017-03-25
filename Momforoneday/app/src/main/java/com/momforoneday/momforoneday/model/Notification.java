package com.momforoneday.momforoneday.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Notification {

    private String _text;
    private String _date;
    private Caregiver _sender;
    private User _receiver;

    public Notification(String _text, Caregiver _sender, User _receiver) {
        this._text = _text;
        this._sender = _sender;
        this._receiver = _receiver;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this._date = dateString + " às " + hourString;
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        String hourString = formatter.format(date);

        this._date = dateString + " às " + hourString;
    }

    public Caregiver getSender() {
        return _sender;
    }

    public void setSender(Caregiver _sender) {
        this._sender = _sender;
    }

    public User getReceiver() {
        return _receiver;
    }

    public void setReceiver(User _receiver) {
        this._receiver = _receiver;
    }
}
