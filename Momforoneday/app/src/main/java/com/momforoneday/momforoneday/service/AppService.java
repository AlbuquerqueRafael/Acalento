package com.momforoneday.momforoneday.service;

import android.graphics.Bitmap;
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

    private static Caregiver selectedCaregiver;
    private static Caregiver contractedCaregiver;
    private static Fragment previousFragment;
    private static List<Caregiver> caregiversList = new ArrayList<>();
    private static Bitmap image;

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

    public static void setPreviousFragment(Fragment fragment) {
        previousFragment = fragment;
    }

    public static Fragment getPreviousFragment(){
        return previousFragment;
    }

    public static void setCaregiversList(List<Caregiver> caregiversList) {
        AppService.caregiversList = caregiversList;
    }

    public static List<Caregiver> getCaregiversList() {
        return caregiversList;
    }

    public static void setImage(Bitmap img) {
        image = img;
    }

    public static Bitmap getImage() {
        return image;
    }
}
