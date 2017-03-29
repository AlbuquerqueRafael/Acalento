package com.momforoneday.momforoneday.controller;

import com.momforoneday.momforoneday.model.Caregiver;

import java.util.List;

/**
 * Created by Mafra on 18/02/2017.
 */
public interface OnCaregiverGetDataListener {

    public void onStart();
    public void onSuccess(List<Caregiver> lista);

}