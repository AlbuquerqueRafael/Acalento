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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_notification);
        recyclerView.setAdapter(new CaregiverNotificationAdapter(AppService.getCregiverNotification(), getContext()));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
        imageButton = (ImageButton) rootView.findViewById(R.id.baby_photo_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentImageCapture();
            }
        });

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
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
            getActivity().startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE);
            pathe.delete();
            image.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
