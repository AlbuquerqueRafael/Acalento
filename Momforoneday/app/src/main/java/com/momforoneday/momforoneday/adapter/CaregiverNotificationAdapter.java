package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.momforoneday.momforoneday.holder.CarregiverHolder;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Notification;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rafael on 4/1/2017.
 */

public class CaregiverNotificationAdapter extends RecyclerView.Adapter<CarregiverHolder> {

    private List<Notification> notificationList;
    private Context context;

    public CaregiverNotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    public CarregiverHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_notification, parent, false);

        CarregiverHolder holder = new CarregiverHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CarregiverHolder viewHolder, int position) {
        final Notification currentNotification = notificationList.get(position);

        String text = "";
        String status = currentNotification.getText();

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



        viewHolder.notificationText.setText(currentNotification.getReceiver().getName());
        viewHolder.notificationDate.setText(currentNotification.getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.setMessage("Por favor, me mande uma foto");
                builder.show();

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
}


