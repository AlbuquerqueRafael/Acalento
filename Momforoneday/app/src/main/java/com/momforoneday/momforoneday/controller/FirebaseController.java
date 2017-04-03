package com.momforoneday.momforoneday.controller;

/**
 * Created by gabrielguimo on 28/03/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.momforoneday.momforoneday.MainActivity;
import com.momforoneday.momforoneday.fragment.NotificationFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.controller.OnGetPhotoListener;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class FirebaseController {

    private final static String LOGGED_CAREGIVER = "Carla Ferreira";
    private final static String CAREGIVERS = "caregivers";
    private final static String CONTRACT = "contract";
    private final static String NOTIFICATIONS = "notifications";
    private final static String PHOTO = "photoURL";
    public static final String FIREBASE_URL = "https://mom-for-one-day.firebaseio.com/";
    public static final String FIREBASE_STORAGE = "gs://mom-for-one-day.appspot.com";
    private static Firebase firebase;
    private static FirebaseStorage firebaseStorage;
    private static SecureRandom random = new SecureRandom();

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

    public static void storageBabyImage(Intent data, MainActivity activity, final String text){

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
       // StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Photos").child(NotificationFragment.path.toString());
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("images/ + " +
                new BigInteger(130, random).toString(32) + ".jpg");


        riversRef.putFile(data.getData())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //if the upload is successfull
                        //hiding the progress dialog
                        Notification notification = new Notification(text,
                                "Carla Ferreira", AppService.getCurrentUser(),taskSnapshot.getDownloadUrl().toString());
                        FirebaseController.updateUserNotification(notification);
                        AppService.sendNotification(FirebaseInstanceId.getInstance().getToken(), "Você Recebeu uma nova notificação", "Nova foto");
                        progressDialog.dismiss();

                        //and displaying a success toast
                      //  Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss();

                        //and displaying error message
                       // Toast.makeText(activity), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
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

    public static void sendNotification(Caregiver caregiver){

        Firebase firebaseRef = getFirebase();
        Firebase notificationReff = firebaseRef.child(CAREGIVERS).child(caregiver.getName()).child(CONTRACT).child(NOTIFICATIONS);

        Notification not = new Notification("Olá, você poderia enviar uma foto do meu bebe",
                AppService.getContractedCaregiver().getName(), AppService.getCurrentUser(), FirebaseInstanceId.getInstance().getToken());

        List<Notification> notifications = caregiver.getContract().getNotifications();
        notifications.add(not);

        notificationReff.setValue(notifications);
    }

    public static void requestPhoto(Contract contract) {
        Firebase firebaseRef = getFirebase();
        Firebase photoRef = firebaseRef.child(CAREGIVERS).child(contract.getCaregiver()).child(CONTRACT).child(NOTIFICATIONS);

//        Notification not = new Notification("Olá, você poderia enviar uma foto do meu bebe",
//                AppService.getCurrentUser().getName(), FirebaseInstanceId.getInstance().getToken());
//
//        photoRef.push().setValue(not);
    }

    public static Caregiver getCaregiverLogged(final OnGetCaregiverListener listener){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverRef = firebaseRef.child(CAREGIVERS).child(LOGGED_CAREGIVER);

        Caregiver caregiver;

        caregiverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    listener.onSuccess(dataSnapshot.getValue(Caregiver.class));
                }catch (Exception e){
                    listener.onSuccess(null);
                }
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

                for (DataSnapshot noti : dataSnapshot.getChildren()){
                    try {
                        Caregiver order = noti.getValue(Caregiver.class);
                        lista.add(order);
                    }catch (Exception e){

                    }
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

    public static void requestImage(Contract contract, final OnGetPhotoListener listener) {
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
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS).child("Carla Ferreira").child(CONTRACT).child(NOTIFICATIONS);

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

    public static void retrieveUserNotifications(String user, final OnNotificationGetDataListener listener) {
        final List<Notification> lista = new ArrayList<>();
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child("users").child("Gabriel Guimaraes").child(NOTIFICATIONS);

        caregiverReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> orderChildren = dataSnapshot.getChildren();
                for (DataSnapshot ord : orderChildren){
                    Notification order = ord.getValue(Notification.class);

                    lista.add(order);


                }

                listener.onSuccess(lista);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
