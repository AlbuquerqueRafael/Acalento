package com.momforoneday.momforoneday.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverNotificationAdapter;
import com.momforoneday.momforoneday.adapter.NotificationAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.OnNotificationGetDataListener;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.service.ImageProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class RecentNotificationsFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<Notification> notificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_recent, container, false);

        FloatingActionButton backBtn = (FloatingActionButton) rootView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new ContractsFragment());
                fragmentTransaction.commit();
            }
        });



        initNotificationsList();

        return rootView;
    }


    private void initNotificationsList(){
        FirebaseController.retrieveUserNotifications(AppService.getCurrentUser().getName(), new OnNotificationGetDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(List<Notification> lista) {
                notificationList = new ArrayList<>();

                for (Notification n : lista) {
                    notificationList.add(n);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
                recyclerView.setAdapter(new NotificationAdapter(notificationList, getContext(), (AppCompatActivity) getActivity()));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layout);

            }
        });
    }

}
