package com.momforoneday.momforoneday.controller;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Mafra on 17/02/2017.
 */

public class FirebaseController {

    private final static String PHOTOS = "photos";
    public static final String FIREBASE_URL = "https://mom-for-one-day.firebaseio.com/";
    private static SecureRandom random = new SecureRandom();

    private static Firebase firebase;

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase(FIREBASE_URL);
        }
        return firebase;
    }


    public static void retrievePharmaOrders(final onGetPhotoListener listener) {
        Firebase firebaseRef = getFirebase();
        final List<String> photos = new ArrayList<String>();

        firebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot.getKey().equals(PHOTOS)){
                    Iterable<DataSnapshot> orderChildren = dataSnapshot.getChildren();

                    for (DataSnapshot ord : orderChildren){
                        photos.add(ord.getValue(String.class));
                    }

                    Random randomizer = new Random();
                    String randomUrl = photos.get(randomizer.nextInt(photos.size()));

                    listener.onSuccess(randomUrl);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }


        });
    }


}