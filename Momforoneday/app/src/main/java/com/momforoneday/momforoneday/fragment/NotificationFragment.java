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
    private Uri path;
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


                Bitmap imageBitmap = null;
                try {
                    InputStream image_stream = getActivity().getContentResolver().openInputStream(path);
                    imageBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(path));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println(imageBitmap);
                System.out.println(imageBitmap);
                System.out.println(imageBitmap);
                System.out.println(imageBitmap);
                System.out.println(imageBitmap);
                System.out.println(imageBitmap);
                //  Bitmap imageBitmap = decodeFile(NotificationFragment.path);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //image.delete();
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // image.delete();
                        dialog.cancel();
                    }
                });

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.image_dialog, null);
                final ImageView photoImage = (ImageView) view.findViewById(R.id.photo_baby);
                photoImage.setImageBitmap(imageBitmap);

                builder.setView(view);
                builder.show();


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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initInputTextBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Comentário: ");

        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text = input.getText().toString();
                System.out.println(text);
                System.out.println(text);

                System.out.println(text);
                System.out.println(text);

                System.out.println(text);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}
