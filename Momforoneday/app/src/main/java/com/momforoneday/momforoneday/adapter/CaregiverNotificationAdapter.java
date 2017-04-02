package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.model.Notification;

import java.util.List;

/**
 * Created by Rafael on 4/1/2017.
 */

public class CaregiverNotificationAdapter extends RecyclerView.Adapter {

    private List<Notification> notificationList;
    private Context context;

    public CaregiverNotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_notification, parent, false);

        NotificationHolder holder = new NotificationHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        NotificationHolder holder = (NotificationHolder) viewHolder;

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



        holder.notificationText.setText(currentNotification.getSender());
        holder.notificationDate.setText(currentNotification.getDate());

    }

    private String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }
}


class NotificationHolder extends RecyclerView.ViewHolder {

    final TextView notificationText;
    final TextView notificationDate;

    public NotificationHolder(View view) {
        super(view);

        notificationText = (TextView) view.findViewById(R.id.user_notification);
        notificationText.setSelected(true);
        notificationText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        notificationText.setSingleLine(true);
        notificationText.setMarqueeRepeatLimit(5);
        notificationText.setSelected(true);

        notificationDate = (TextView) view.findViewById(R.id.notification_date);

    }

}