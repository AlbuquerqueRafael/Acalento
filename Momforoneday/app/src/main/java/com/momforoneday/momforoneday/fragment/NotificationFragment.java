package com.momforoneday.momforoneday.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.ImageAdapter;
import com.momforoneday.momforoneday.service.NotificationsService;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private View rootView;
    private GridView gridView;

    static final String[] CHILDREN_STATUS = new String[] {
            "comendo", "chorando", "brincando", "remedio", "dormindo"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        setUI();

        return rootView;
    }

    private void setUI(){
        gridView = (GridView) rootView.findViewById(R.id.grid_notifications);

        NotificationsService.clearNotificationPrefference();
        gridView.setAdapter(new ImageAdapter(getContext(), CHILDREN_STATUS));
    }

}
