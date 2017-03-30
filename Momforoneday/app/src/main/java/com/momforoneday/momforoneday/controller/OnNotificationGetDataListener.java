package com.momforoneday.momforoneday.controller;

import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.model.Notification;

import java.util.List;

/**
 * Created by Rafael on 3/15/2017.
 */

public interface OnNotificationGetDataListener {

    public void onStart();
    public void onSuccess(List<Notification> lista);

}