package com.momforoneday.momforoneday.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverNotificationAdapter;
import com.momforoneday.momforoneday.adapter.NotificationAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.OnNotificationGetDataListener;
import com.momforoneday.momforoneday.controller.onGetPhotoListener;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class ContractsFragment extends Fragment {

    private View rootView;
    private TextView caregiverName;
    private ImageView contractStatus;
    private TextView requestImageButton;
    private RecyclerView recyclerView;
    private TextView notificationText;
    private RelativeLayout notificationLayout;
    private TextView notificationDate;
    private FloatingActionButton perfilButton;
    private FloatingActionButton seePhoto;
    private ImageView requestedImage;
    private TextView recentNotifications;
    private TextView requestLastPic;
    private List<Notification> notificationList;
    private BottomNavigationView navigationBar;
    private View gradientView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_my_contracts, container, false);

        caregiverName = (TextView) rootView.findViewById(R.id.caregiver_name);
        contractStatus = (ImageView) rootView.findViewById(R.id.contract_status);
        requestImageButton = (TextView) rootView.findViewById(R.id.request_image);
        requestLastPic = (TextView) rootView.findViewById(R.id.show_last_pict);
        perfilButton = (FloatingActionButton) rootView.findViewById(R.id.perfil_btn);
        requestedImage = (ImageView) rootView.findViewById(R.id.image_to_show);
        recentNotifications = (TextView) rootView.findViewById(R.id.recent_notifications);
        notificationText = (TextView) rootView.findViewById(R.id.notification_text);
        notificationDate = (TextView) rootView.findViewById(R.id.notification_date);

        notificationLayout = (RelativeLayout) rootView.findViewById(R.id.notification_layout);
        requestedImage.setVisibility(View.GONE);
        notificationLayout.setVisibility(View.GONE);
        navigationBar = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        gradientView = getActivity().findViewById(R.id.gradient_view);

        navigationBar.setVisibility(View.VISIBLE);
        gradientView.setVisibility(View.VISIBLE);


        recentNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new RecentNotificationsFragment());
                fragmentTransaction.commit();
            }
        });

        perfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppService.setPreviousFragment(new ContractsFragment());
                AppService.setSelectedCaregiver(AppService.getContractedCaregiver());

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new CaregiverDetailFragment());
                fragmentTransaction.commit();
            }
        });

        requestImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImage();
            }
        });

        requestLastPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseController.retrieveUserNotifications(AppService.getCurrentUser().getName(), new OnNotificationGetDataListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(List<Notification> lista) {
                        try {
                            notificationList = new ArrayList<>();
                            Log.v("Erro", "td");
                            Log.v("Erro", "td");

                            Log.v("Erro", "td");

                            Log.v("Erro", "td");

                            if (lista.size() > 0) {
                                requestedImage.setVisibility(View.VISIBLE);
                                notificationLayout.setVisibility(View.VISIBLE);

                                notificationText.setText(lista.get(lista.size() - 1).getSender());
                                notificationDate.setText(lista.get(lista.size() - 1).getDate());
                                requestedImage.setVisibility(View.VISIBLE);
                                Log.v("Erro", "https://firebasestorage.googleapis.com/v0/b/mom-for-one-day.appspot.com/o/images%2F+%2B+a4ct2t6lhj6ipksaot8ctq8u6s.jpg?alt=media&token=aecb50a2-3a45-4244-8e64-a85afdac1825");
                                Log.v("Erro", lista.get(lista.size() - 1).getImage().toString());
                                Glide.with(getContext()).load(lista.get(lista.size() - 1).getImage().toString()).into(requestedImage);
                                //                             Picasso.with(getContext())
                                //                                 .load(lista.get(lista.size() -1).getImage())
                                //                            .resize(200,200)
                                //                                .centerCrop().into(requestedImage);
                            }
                        }catch (Exception e){
                            
                        }


                    }
                });
            }
        });



        //setUpRecycler();

        if (AppService.getContractedCaregiver() != null) {

            Caregiver caregiver = AppService.getContractedCaregiver();

            caregiverName.setText(caregiver.getName());

            checkStatus(caregiver);

            if (AppService.getLastRequestedPhoto() != null) {
                //requestedImage.setVisibility(View.VISIBLE);

//                Picasso.with(getContext())
//                        .load(AppService.getLastRequestedPhoto())
//                        .resize(260, 200)
//                        .centerCrop().into(requestedImage);
            }
        }

        return rootView;
    }

    private String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }


    private void requestImage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setMessage("Solicitação feito, logo mais você receberá uma notificação");
        builder.show();

        FirebaseController.requestPhoto(AppService.getContractedCaregiver().getContract());
    }

    private void checkStatus(Caregiver caregiver) {

        String status = caregiver.getContract().getStatus();

//        if (status == "Pendente") {
//            contractStatus.setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY );
//        } else if (status == "Rejeitado") {
//            contractStatus.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY );
//        } else if (status == "Finalizado") {
//            contractStatus.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY );
//        } else if (status == "Em andamento") {
//            contractStatus.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY );
//        }

    }

    private void initNotificationsList(){
        FirebaseController.retrieveUserNotifications(AppService.getCurrentUser().getName(), new OnNotificationGetDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(List<Notification> lista) {
                notificationList = new ArrayList<>();

                for(int i = lista.size() - 1; i >= 1; i--){
                    notificationList.add(lista.get(i));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

            }
        });
    }
}
