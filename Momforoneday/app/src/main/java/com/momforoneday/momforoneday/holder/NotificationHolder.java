package com.momforoneday.momforoneday.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.momforoneday.momforoneday.R;

/**
 * Created by Rafael on 4/2/2017.
 */

public class NotificationHolder extends RecyclerView.ViewHolder {

    public final TextView notificationText;
    public final TextView notificationDate;

    public NotificationHolder(View itemView) {
        super(itemView);

        notificationText = (TextView) itemView.findViewById(R.id.user_notification_text);
        notificationDate = (TextView) itemView.findViewById(R.id.user_notification_date);

    }

}