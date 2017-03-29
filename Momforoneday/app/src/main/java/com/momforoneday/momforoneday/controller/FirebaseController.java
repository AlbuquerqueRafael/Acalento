package com.momforoneday.momforoneday.controller;

/**
 * Created by gabrielguimo on 28/03/17.
 */

import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class FirebaseController {

    private final static String CAREGIVERS = "caregivers";
    public static final String FIREBASE_URL = "https://mom-for-one-day.firebaseio.com/";
    private static Firebase firebase;

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase(FIREBASE_URL);
        }
        return firebase;
    }

    public static void retrieveCaregivers(final OnCaregiverGetDataListener listener){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS);

        caregiverReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Caregiver> lista = new ArrayList<>();

                for (DataSnapshot care : snapshot.getChildren()){
                    Caregiver caregiver = care.getValue(Caregiver.class);
                    lista.add(caregiver);
                }

                listener.onSuccess(lista);

            }

            @Override public void onCancelled(FirebaseError error) { }
        });

        caregiverReff.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Iterable<DataSnapshot> productChildren = dataSnapshot.getChildren();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}


        });

    }

    public static void saveCaregiver(Caregiver caregiver){
        Firebase firebaseRef = getFirebase();
        Firebase caregiverReff = firebaseRef.child(CAREGIVERS);


        caregiverReff.child(caregiver.getName()).setValue(caregiver);

    }

    public static void updateCaregiver(Caregiver caregiver) {
        Firebase firebaseRef = getFirebase();

        Firebase caregiverReff = firebaseRef.child(CAREGIVERS);

        caregiverReff.child(caregiver.getName()).setValue(caregiver);

    }

}
