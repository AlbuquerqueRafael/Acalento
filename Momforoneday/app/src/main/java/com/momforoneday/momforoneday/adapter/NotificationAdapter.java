package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.holder.NotificationHolder;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.ExifUtil;
import com.momforoneday.momforoneday.service.ImageProvider;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private List<Notification> notificationList;
    private Context context;
    private AppCompatActivity activity;
    private View view;
    private LayoutInflater inflater;
    public NotificationAdapter(List<Notification> notificationList, Context context, AppCompatActivity activity) {
        this.notificationList = notificationList;
        this.activity = activity;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        NotificationHolder holder = new NotificationHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationHolder viewHolder, int position) {



        final Notification currentNotification = notificationList.get(position);

//        String text = "";
//        String status = currentNotification.getText();
//
//        if (status.equals("comendo")) {
//            text = getEmojiByUnicode(0x1F37C) + "  Mamãe, estou comendo! Nham nham";
//        } else if (status.equals("chorando")) {
//            text = getEmojiByUnicode(0x1F62D) + "  Mamãeeee, estou chorando! Buáááá";
//        } else if (status.equals("brincando")) {
//            text = getEmojiByUnicode(0x1F61D) + "  Olha mamãe, estou brincando! Hihi";
//        } else if (status.equals("remedio")) {
//            text = getEmojiByUnicode(0x1F48A) + "  Mamãe, estou tomando o remédio! Argh";
//        } else if (status.equals("dormindo")) {
//            text = getEmojiByUnicode(0x1F634) + "  Mamãe, estou indo dormir! Zzzzz";
//        }

        viewHolder.notificationText.setText(currentNotification.getSender());
        viewHolder.notificationDate.setText(currentNotification.getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                LayoutInflater infl = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                View imageView = infl.inflate(R.layout.image_dialog, null);
                ImageView photoImage = (ImageView) imageView.findViewById(R.id.photo_baby);


                Log.v("Erro",currentNotification.getImage().substring(currentNotification.getImage().length() - 1));
                byte[] decodedString = Base64.decode(currentNotification.getImage(), Base64.DEFAULT);
                Log.v("Erro", Arrays.toString(decodedString));
                byte[] ss = Base64.decode(currentNotification.getImage(), Base64.NO_WRAP);
                Log.v("Erro", Arrays.toString(ss));

                Bitmap imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                //imageBitmap = ImageProvider.convertToBitmap(currentNotification.getImage());

                photoImage.setBackgroundColor(Color.WHITE);

                photoImage.setImageBitmap(imageBitmap);
                photoImage.setBackgroundColor(Color.WHITE);

                Log.v("Erro", "Uno");
                builder.show();
                Log.v("Erro", "Dono");
            }
        });

    }

    private String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }

    private Uri convertImageToUri(String base64Image){
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap inImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }
}


