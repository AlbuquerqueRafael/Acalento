package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.service.AppService;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class HomeFragment extends Fragment {

    private View rootView;
    private TextView toolbarTextView;
    private AppCompatButton scheduleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        toolbarTextView = (TextView) rootView.findViewById(R.id.toolbar_tv);
        toolbarTextView.setText(AppService.getCurrentUser());

        scheduleButton = (AppCompatButton) rootView.findViewById(R.id.horarios_btn);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new ContractListFragment());
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
