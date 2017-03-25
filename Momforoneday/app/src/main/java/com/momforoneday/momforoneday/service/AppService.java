package com.momforoneday.momforoneday.service;

import android.support.v4.app.Fragment;

import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class AppService {

    private static String currentUser;
    private static List<Caregiver> caregivers = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();
    private static Caregiver selectedCaregiver;
    private static Caregiver contractedCaregiver;
    private static Fragment previousFragment;

    public static void setCurrentUser(String user){
        currentUser = user;
    }

    public static String getCurrentUser(){
        return currentUser;
    }

    public static List<Caregiver> getCuidadores(){

        Caregiver car;
        List<Comment> comments;

        for (int i = 0; i < 10; i++) {
            car = new Caregiver("Nome " + i, "Email " + i, "Telefone " + i, i + 20);
            comments = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Comment comment = new Comment("Nome " + j, "Topp");
                comments.add(comment);
            }

            car.setComments(comments);
            caregivers.add(car);
        }

        return caregivers;
    }

    public static void setSelectedCaregiver(Caregiver caregiver){
        selectedCaregiver = caregiver;
    }

    public static Caregiver getSelectedCaregiver() {
        return selectedCaregiver;
    }

    public static void setContractedCaregiver(Caregiver caregiver){
        contractedCaregiver = caregiver;
    }

    public static Caregiver getContractedCaregiver() {
        return contractedCaregiver;
    }


    public static List<Notification> getNotifications() {

        Notification notification;

        String[] CHILDREN_STATUS = new String[] {
                "comendo", "chorando", "brincando", "remedio", "dormindo"};

        for (int i = 0; i < 5; i++) {
            notification = new Notification(CHILDREN_STATUS[i], getContractedCaregiver(), new User("User " + 1, "Email " + 1));
            notifications.add(notification);
        }

        return notifications;
    }

    public static void setPreviousFragment(Fragment fragment) {
        previousFragment = fragment;
    }

    public static Fragment getPreviousFragment(){
        return previousFragment;
    }
}
