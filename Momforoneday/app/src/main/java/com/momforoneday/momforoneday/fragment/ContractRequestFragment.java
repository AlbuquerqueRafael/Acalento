package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.adapter.CaregiverAdapter;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.controller.OnGetCaregiverListener;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.service.AppService;

/**
 * Created by gustavooliveira on 4/1/17.
 */

public class ContractRequestFragment extends Fragment {

    private View rootView;
    private LinearLayout contract_layout;
    private TextView parent_name;
    private BottomNavigationView navigationBar;
    private View gradientView;

    private AppCompatButton accept_btn;
    private AppCompatButton reject_btn;


    private Caregiver caregiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contract_request, container, false);
        contract_layout = (LinearLayout) rootView.findViewById(R.id.request_view);
        parent_name = (TextView) rootView.findViewById(R.id.caregiver_name);
        accept_btn = (AppCompatButton) rootView.findViewById(R.id.accept_btn);
        reject_btn = (AppCompatButton) rootView.findViewById(R.id.reject_btn);



        navigationBar = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        gradientView = getActivity().findViewById(R.id.gradient_view);

        navigationBar.setVisibility(View.VISIBLE);
        gradientView.setVisibility(View.VISIBLE);

        getContracts();


        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contract contract = caregiver.getContract();

                contract.setStatus("Aceito");

                FirebaseController.setContract(contract);
                contract_layout.setVisibility(View.GONE);
            }
        });

        reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contract contract = caregiver.getContract();

                contract.setStatus("Rejeitado");

                FirebaseController.setContract(contract);
                contract_layout.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    private void getContracts() {
        FirebaseController.getCaregiverLogged(new OnGetCaregiverListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Caregiver cg) {
                caregiver = cg;
                try {
                    Contract contract = caregiver.getContract();

                    if (isPendente(caregiver)) {
                        contract_layout.setVisibility(View.VISIBLE);

                        parent_name.setText(contract.getUser().getName());

                    } else if (contract_layout.getVisibility() == View.VISIBLE) {
                        contract_layout.setVisibility(View.GONE);
                    }
                }catch (Exception e){

                }
            }
        });
    }

    private static boolean isPendente(Caregiver caregiver) {
        return caregiver.getContract().getStatus().equals("Pendente");
    }
}
