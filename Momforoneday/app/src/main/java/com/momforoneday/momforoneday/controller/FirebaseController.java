package com.momforoneday.momforoneday.controller;

/**
 * Created by gabrielguimo on 28/03/17.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;

import java.util.ArrayList;
import java.util.List;

public class FirebaseController {

    private final static String LOGGED_CAREGIVER = "Carla Ferreira";
    private final static String CAREGIVERS = "caregivers";
    private final static String CONTRACT = "contract";
    private final static String NOTIFICATIONS = "notifications";
    private final static String PHOTO = "photoURL";
    public static final String FIREBASE_URL = "https://mom-for-one-day.firebaseio.com/";
    private static Firebase firebase;

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase(FIREBASE_URL);
        }
        return firebase;
    }

    public static void storageImage(final Contract contract, Intent data){

        final FirebaseStorage storage = FirebaseStorage.getInstance();

        storage.getReference().child(contract.getCaregiver()).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference().child(contract.getCaregiver()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        FirebaseController.sendImage(contract, url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {}
                });
            }
        });
    }

    public static void endContract(Contract contract) {
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS).child(contract.getCaregiver()).child(CONTRACT);

        caregiverReff.removeValue();
    }

    public static void setContract(Contract contract) {
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS).child(contract.getCaregiver()).child(CONTRACT);

        caregiverReff.setValue(contract);
    }

    public static void sendNotification(Caregiver caregiver, Notification notification){

        Firebase firebaseRef = getFirebase();
        Firebase notificationReff = firebaseRef.child(CAREGIVERS).child(notification.getSender()).child(CONTRACT).child(NOTIFICATIONS);

        List<Notification> notifications = caregiver.getContract().getNotifications();
        notifications.add(notification);

        notificationReff.setValue(notifications);
    }

    public static Caregiver getCaregiverLogged(final OnGetCaregiverListener listener){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverRef = firebaseRef.child(CAREGIVERS).child(LOGGED_CAREGIVER);

        Caregiver caregiver;

        caregiverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot.getValue(Caregiver.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return null;
    }




    public static void retrieveCaregivers(final OnCaregiverGetDataListener listener){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS);

        caregiverReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Caregiver> lista = new ArrayList<>();

                for (DataSnapshot care : dataSnapshot.getChildren()){
                    Caregiver caregiver = care.getValue(Caregiver.class);
                    lista.add(caregiver);
                }

                listener.onSuccess(lista);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public static void saveCaregiver(Caregiver caregiver){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS);


        caregiverReff.child(caregiver.getName()).setValue(caregiver);
    }

    public static void requestImage(Contract contract, final onGetPhotoListener listener) {
        Firebase firebaseRef = getFirebase();
        Firebase photoRef = firebaseRef.child(CAREGIVERS).child(contract.getCaregiver()).child(CONTRACT).child(PHOTO);

        photoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String photoURL = dataSnapshot.getValue().toString();

                listener.onSuccess(photoURL);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void sendImage(Contract contract, String url) {
        Firebase firebaseRef = getFirebase();
        Firebase photoRef = firebaseRef.child(CAREGIVERS).child(contract.getCaregiver()).child(CONTRACT).child(PHOTO);

        photoRef.setValue(url);
    }

    public static void updateUserNotification(Notification notification){
        Firebase firebaseRef = getFirebase();
        Firebase photoRef = firebaseRef.child("users").child("Gabriel Guimaraes").child(NOTIFICATIONS);


        photoRef.push().setValue(notification);
    }

    public static void retrieveNotifications(Caregiver contractedCaregiver, final OnNotificationGetDataListener listener) {

        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS).child(contractedCaregiver.getName()).child(CONTRACT).child(NOTIFICATIONS);

        caregiverReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notification> lista = new ArrayList<>();

                for (DataSnapshot noti : dataSnapshot.getChildren()){
                    Notification notification = noti.getValue(Notification.class);
                    lista.add(notification);
                }

                listener.onSuccess(lista);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
