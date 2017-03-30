package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.OnCaregiverGetDataListener;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class ContractListFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<Caregiver> caregivers;
    private FloatingActionButton backBtn;
    private BottomNavigationView navigationBar;
    private View gradientView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contracts, container, false);

        backBtn = (FloatingActionButton) rootView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new HomeFragment());
                fragmentTransaction.commit();
            }
        });


        navigationBar = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        gradientView = getActivity().findViewById(R.id.gradient_view);

        navigationBar.setVisibility(View.VISIBLE);
        gradientView.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        if (AppService.getCaregiversList().size() == 0) {
            initCaregiverList();
        } else {
            recyclerView.setAdapter(new CaregiverAdapter(AppService.getCaregiversList(), getContext()));

            RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(layout);
        }

        return rootView;
    }

    private void initCaregiverList() {

        FirebaseController.retrieveCaregivers(new OnCaregiverGetDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<Caregiver> lista) {
                caregivers = new ArrayList<>();

                for (Caregiver c : lista) {
                    caregivers.add(c);
                    if (c.getContract() != null) {
                        if (c.getContract().getUser().getEmail().equals(AppService.getCurrentUser().getEmail())) {
                            AppService.setContractedCaregiver(c);
                        }
                    }
                }

                recyclerView.setAdapter(new CaregiverAdapter(caregivers, getContext()));

                AppService.setCaregiversList(caregivers);

                RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layout);

            }
        });
    }
}
