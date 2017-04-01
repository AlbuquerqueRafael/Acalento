package com.momforoneday.momforoneday.controller;

import com.momforoneday.momforoneday.model.Caregiver;

/**
 * Created by gustavooliveira on 4/1/17.
 */

public interface OnGetCaregiverListener {

    public void onStart();
    public void onSuccess(Caregiver caregiver);
}
