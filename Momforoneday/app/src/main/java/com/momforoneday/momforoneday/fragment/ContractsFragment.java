package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverAdapter;
import com.momforoneday.momforoneday.adapter.NotificationAdapter;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class ContractsFragment extends Fragment {

    private View rootView;
    private LinearLayout layoutNoContracts;
    private LinearLayout layoutHaveContracts;
    private TextView caregiverName;
    private TextView contractStatus;
    private AppCompatButton requestImageButton;
    private RecyclerView recyclerView;
    private FloatingActionButton perfilButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_my_contracts, container, false);

        layoutNoContracts = (LinearLayout) rootView.findViewById(R.id.no_contracts);
        layoutHaveContracts = (LinearLayout) rootView.findViewById(R.id.hv_contracts);
        caregiverName = (TextView) rootView.findViewById(R.id.caregiver_name);
        contractStatus = (TextView) rootView.findViewById(R.id.contract_status);
        requestImageButton = (AppCompatButton) rootView.findViewById(R.id.request_image);
        perfilButton = (FloatingActionButton) rootView.findViewById(R.id.perfil_btn);

        if (AppService.getContractedCaregiver() == null) {

            AppCompatButton searchContracts = (AppCompatButton) rootView.findViewById(R.id.search_contracts);
            searchContracts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new HomeFragment());
                    fragmentTransaction.commit();

                    BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);

                    View view = navigation.findViewById(R.id.navigation_home);
                    view.performClick();
                }
            });

            layoutNoContracts.setVisibility(View.VISIBLE);
            layoutHaveContracts.setVisibility(View.GONE);
        } else {
            layoutNoContracts.setVisibility(View.GONE);
            layoutHaveContracts.setVisibility(View.VISIBLE);

            final Caregiver caregiver = AppService.getContractedCaregiver();

            perfilButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppService.setPreviousFragment(new ContractsFragment());
                    AppService.setSelectedCaregiver(caregiver);

                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new CaregiverDetailFragment());
                    fragmentTransaction.commit();
                }
            });

            caregiverName.setText(caregiver.getName());
            contractStatus.setText(caregiver.getContract().getStatus());

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

            List<Notification> notifications = new ArrayList<>();

            recyclerView.setAdapter(new NotificationAdapter(notifications, getContext()));

            requestImageButton.setText("\uD83D\uDCF7  Me envia uma foto!");

            RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(layout);
        }

        return rootView;
    }
}
