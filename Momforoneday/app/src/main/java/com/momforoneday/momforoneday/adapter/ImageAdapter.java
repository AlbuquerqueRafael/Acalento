package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.service.NotificationsService;

/**
 * Created by gabrielguimo on 14/03/17.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private final String[] childrenStatus;

    public ImageAdapter(Context context, String[] childrenStatus) {
        this.context = context;
        this.childrenStatus = childrenStatus;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            final Holder holder = new Holder();

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.item_notification, null);

            holder._textTitle = (TextView) gridView.findViewById(R.id.grid_item_title);
            holder._emojiButton = (Button) gridView.findViewById(R.id.grid_item_emoji);

            String status = childrenStatus[position];

            if (status.equals("comendo")) {
                holder._textTitle.setText("Mamãe, estou comendo! Nham nham");
                holder._emojiButton.setText(getEmojiByUnicode(0x1F37C));
            } else if (status.equals("chorando")) {
                holder._textTitle.setText("Mamãeeee, estou chorando! Buáááá");
                holder._emojiButton.setText(getEmojiByUnicode(0x1F62D));
            } else if (status.equals("brincando")) {
                holder._textTitle.setText("Olha mamãe, estou brincando! Hihi");
                holder._emojiButton.setText(getEmojiByUnicode(0x1F61D));
            } else if (status.equals("remedio")) {
                holder._textTitle.setText("Mamãe, estou tomando o remédio! Argh");
                holder._emojiButton.setText(getEmojiByUnicode(0x1F48A));
            } else if (status.equals("dormindo")) {
                holder._textTitle.setText("Mamãe, estou indo dormir! Zzzzz");
                holder._emojiButton.setText(getEmojiByUnicode(0x1F634));
            }

            holder._emojiButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, holder._textTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            holder._emojiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getScaleX() == 1.2f) {
                        v.animate().scaleX(1f).scaleY(1f).start();
                        String notificationName = getNotificationName(holder._textTitle.getText().toString());
                        NotificationsService.removeNotificationPrefference(notificationName);
                    } else {
                        v.animate().scaleX(1.2f).scaleY(1.2f).start();
                        String notificationName = getNotificationName(holder._textTitle.getText().toString());
                        NotificationsService.addNotificationPrefference(notificationName);
                    }
                }
            });

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    private String getNotificationName(String message) {
        switch (message){
            case "Mamãe, estou comendo! Nham nham":
                return "comendo";
            case "Mamãeeee, estou chorando! Buáááá":
                return "chorando";
            case "Olha mamãe, estou brincando! Hihi":
                return "brincando";
            case "Mamãe, estou tomando o remédio! Argh":
                return "remedio";
            case "Mamãe, estou indo dormir! Zzzzz":
                return "dormindo";
            default:
                return "none";
        }
    }

    public class Holder {
        TextView _textTitle;
        TextView _emojiButton;
    }


    private String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    @Override
    public int getCount() {
        return childrenStatus.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
