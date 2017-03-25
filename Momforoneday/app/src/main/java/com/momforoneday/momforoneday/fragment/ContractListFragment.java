package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverAdapter;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.service.AppService;

import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class ContractListFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<Caregiver> caregivers;
    private FloatingActionButton filterBtn;
    private FloatingActionButton backBtn;

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

        filterBtn = (FloatingActionButton) rootView.findViewById(R.id.filter_btn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Filtrar", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        caregivers = AppService.getCuidadores();

        recyclerView.setAdapter(new CaregiverAdapter(caregivers, getContext()));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        return rootView;
    }
}
