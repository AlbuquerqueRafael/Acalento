package com.momforoneday.momforoneday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class Contract {

    private String status;
    private String caregiver;
    private User user;
    private String schedule;
    private List<Notification> notifications;
    private String photoURL;

    public Contract(){
        this.notifications = new ArrayList<>();
    }

    public Contract(String caregiver, User user, String schedule) {
        this.caregiver = caregiver;
        this.user = user;
        this.schedule = schedule;
        this.status = "Pendente";
        this.notifications = new ArrayList<>();
        this.photoURL = "";
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
