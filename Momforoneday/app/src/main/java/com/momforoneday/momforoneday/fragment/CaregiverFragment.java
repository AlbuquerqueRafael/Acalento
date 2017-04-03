package com.momforoneday.momforoneday.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.momforoneday.momforoneday.Manifest;
import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gabrielguim on 03/04/2017.
 */

public class CaregiverFragment extends Fragment {

    public static Uri path;
    private File image;
    private View rootView;
    public static final Integer CAPTURE_IMAGE_REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.caregiver_fragment, container, false);

        Button chorando = (Button) rootView.findViewById(R.id.chorando);
        Button comendo = (Button) rootView.findViewById(R.id.comendo);
        Button remedio = (Button) rootView.findViewById(R.id.remedio);
        Button brincando = (Button) rootView.findViewById(R.id.brincando);
        Button dormindo = (Button) rootView.findViewById(R.id.dormindo);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((Button) v).getText().toString();
                FirebaseController.sendNotification(AppService.getContractedCaregiver(), new Notification(text, AppService.getContractedCaregiver().getName(), AppService.getCurrentUser()));
            }
        };

        chorando.setOnClickListener(listener);
        comendo.setOnClickListener(listener);
        remedio.setOnClickListener(listener);
        brincando.setOnClickListener(listener);
        dormindo.setOnClickListener(listener);

        Button foto = (Button) rootView.findViewById(R.id.mandar_foto);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        return rootView;
    }

    public void takePhoto(View view) {
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

}
