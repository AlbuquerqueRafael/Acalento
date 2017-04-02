package com.momforoneday.momforoneday.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.momforoneday.momforoneday.R;
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
    private ImageView requestedImage;
    private TextView recentNotifications;
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
        perfilButton = (FloatingActionButton) rootView.findViewById(R.id.perfil_btn);
        requestedImage = (ImageView) rootView.findViewById(R.id.image_to_show);
        recentNotifications = (TextView) rootView.findViewById(R.id.recent_notifications);
        notificationText = (TextView) rootView.findViewById(R.id.notification_text);
        notificationDate = (TextView) rootView.findViewById(R.id.notification_date);
        notificationLayout = (RelativeLayout) rootView.findViewById(R.id.notification_layout);

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

        requestedImage.setVisibility(View.GONE);
        notificationLayout.setVisibility(View.GONE);

        setUpRecycler();

        if (AppService.getContractedCaregiver() != null) {

            Caregiver caregiver = AppService.getContractedCaregiver();

            caregiverName.setText(caregiver.getName());

            checkStatus(caregiver);

            if (AppService.getLastRequestedPhoto() != null) {
                requestedImage.setVisibility(View.VISIBLE);

                Picasso.with(getContext())
                        .load(AppService.getLastRequestedPhoto())
                        .resize(260, 200)
                        .centerCrop().into(requestedImage);
            }
        }

        return rootView;
    }

    private void setUpRecycler() {
        FirebaseController.retrieveNotifications(AppService.getContractedCaregiver(), new OnNotificationGetDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<Notification> lista) {
                notificationList = new ArrayList<>();

                for (Notification n : lista) {
                    notificationList.add(n);
                }

                if (notificationList.isEmpty()){
                    notificationLayout.setVisibility(View.GONE);
                } else {
                    notificationLayout.setVisibility(View.VISIBLE);
                }

                String text = "";
                String status = "";

                if (notificationList.size() > 0) {
                    status = notificationList.get(notificationList.size() - 1).getText();

                    if (status.equals("comendo")) {
                        text = getEmojiByUnicode(0x1F37C) + "  Mamãe, estou comendo! Nham nham";
                    } else if (status.equals("chorando")) {
                        text = getEmojiByUnicode(0x1F62D) + "  Mamãeeee, estou chorando! Buáááá";
                    } else if (status.equals("brincando")) {
                        text = getEmojiByUnicode(0x1F61D) + "  Olha mamãe, estou brincando! Hihi";
                    } else if (status.equals("remedio")) {
                        text = getEmojiByUnicode(0x1F48A) + "  Mamãe, estou tomando o remédio! Argh";
                    } else if (status.equals("dormindo")) {
                        text = getEmojiByUnicode(0x1F634) + "  Mamãe, estou indo dormir! Zzzzz";
                    }

                    notificationText.setText(text);
                    notificationText.setSelected(true);
                    notificationText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    notificationText.setSingleLine(true);
                    notificationText.setMarqueeRepeatLimit(5);
                    notificationText.setSelected(true);

                    notificationDate.setText(notificationList.get(notificationList.size()-1).getDate());
                }

            }
        });
    }

    private String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }


    private void requestImage(){
        FirebaseController.requestImage(AppService.getContractedCaregiver().getContract(), new onGetPhotoListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String url) {

                requestedImage.setVisibility(View.VISIBLE);

                Picasso.with(getContext())
                        .load(url)
                        .resize(260, 200)
                        .centerCrop().into(requestedImage);

                AppService.setLastRequestedPhoto(url);

            }
        });
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
}
