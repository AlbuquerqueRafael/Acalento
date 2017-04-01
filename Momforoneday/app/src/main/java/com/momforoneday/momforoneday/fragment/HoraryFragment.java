package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.HoraryAdapter;
import com.momforoneday.momforoneday.service.AppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 28/03/17.
 */

public class HoraryFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_horary, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        HoraryAdapter horaryAdapter = new HoraryAdapter(getContext(), AppService.getSelectedCaregiver().getSchedules());
        gridView.setAdapter(horaryAdapter);

        return rootView;
    }


}
