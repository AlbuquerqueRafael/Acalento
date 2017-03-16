package com.momforoneday.momforoneday.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.ImageAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.onGetPhotoListener;
import com.momforoneday.momforoneday.service.NotificationsService;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private View rootView;
    private GridView gridView;
    private Button photoButton;

    static final String[] CHILDREN_STATUS = new String[] {
            "comendo", "chorando", "brincando", "remedio", "dormindo"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        photoButton = (Button) rootView. findViewById(R.id.photo_button);
        setUI();

        return rootView;
    }

    private void setUI(){
        gridView = (GridView) rootView.findViewById(R.id.grid_notifications);
        photoButton = (Button) rootView. findViewById(R.id.photo_button);

        photoButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.image_dialog, null);

                setUrlPhoto(builder, view);

            }
        });

        NotificationsService.clearNotificationPrefference();
        gridView.setAdapter(new ImageAdapter(getContext(), CHILDREN_STATUS));
    }

    private void setUrlPhoto(final AlertDialog.Builder builder, final View view){
        final ImageView photoImage = (ImageView) view.findViewById(R.id.photo_baby);

        FirebaseController.retrievePharmaOrders(new onGetPhotoListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String url) {
                Picasso.with(getContext())
                        .load(url)
                        .resize(200, 200)
                        .centerCrop().into(photoImage);
                builder.setView(view);
                builder.show();
            }
        });


    }

}
