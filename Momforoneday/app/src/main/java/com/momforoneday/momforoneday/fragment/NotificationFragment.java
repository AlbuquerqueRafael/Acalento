package com.momforoneday.momforoneday.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.momforoneday.momforoneday.MainActivity;
import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverAdapter;
import com.momforoneday.momforoneday.adapter.CaregiverNotificationAdapter;
import com.momforoneday.momforoneday.adapter.NotificationAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.OnGetCaregiverListener;
import com.momforoneday.momforoneday.controller.OnNotificationGetDataListener;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.dial;
import static android.R.attr.path;

/**
 * Created by Rafael on 4/1/2017.
 */

public class NotificationFragment extends Fragment {
    private View rootView;
    private TextView toolbarTextView;
    private RecyclerView recyclerView;
    private AppCompatButton scheduleButton;
    public static Uri path;
    public static final Integer CAPTURE_IMAGE_REQUEST_CODE = 100;
    private boolean checkPermission = false;
    private String text = "";
    private ImageButton imageButton;
    private File image;
    private TextView textview;

    private List<Notification> notificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        imageButton = (ImageButton) rootView.findViewById(R.id.baby_photo_button);
        textview = (TextView) rootView.findViewById(R.id.contract_text);

        textview.setText("Contrato inativo");
        imageButton.setVisibility(View.GONE);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentImageCapture();
            }
        });

        checkValidContract();
        //initNotificationsList();

        return rootView;
    }

    private void startIntentImageCapture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            SecureRandom random = new SecureRandom();
            File pathe = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);

            image = new File(pathe, new BigInteger(130, random).toString(32) + ".jpeg");
            path = Uri.fromFile(image);
           // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
            getActivity().startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE);
            pathe.delete();
            image.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNotificationsList(){
        FirebaseController.retrieveNotifications(AppService.getContractedCaregiver(), new OnNotificationGetDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(List<Notification> lista) {
                notificationList = new ArrayList<>();

                for(int i = lista.size() - 1; i >= 0; i--){
                    notificationList.add(lista.get(i));
                }

                recyclerView = (RecyclerView) rootView.findViewById(R.id.list_notification);
                recyclerView.setAdapter(new CaregiverNotificationAdapter(notificationList, getContext()));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layout);

            }
        });
    }

    private void checkValidContract(){
        FirebaseController.getCaregiverLogged(new OnGetCaregiverListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Caregiver cg) {
                Caregiver caregiver = cg;
                Contract contract = caregiver.getContract();

                if(!isPendente(caregiver)){
                    textview.setText("Contrato ativo");
                    imageButton.setVisibility(View.VISIBLE);
                    initNotificationsList();
                }
            }
        });

    }

    private static boolean isPendente(Caregiver caregiver) {
        return caregiver.getContract().getStatus().equals("Pendente");
    }



}
