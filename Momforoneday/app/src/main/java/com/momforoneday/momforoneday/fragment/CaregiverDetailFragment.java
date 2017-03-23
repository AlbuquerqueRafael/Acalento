package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class CaregiverDetailFragment extends Fragment {

    private View rootView;
    private TextView caregiverName;
    private TextView caregiverAge;
    private ImageView caregiverImg;
    private AppCompatButton contractButton;
    private TextView caregiverEmail;
    private TextView caregiverPhone;
    private RelativeLayout layoutComments;
    private RelativeLayout layoutRating;

    private Caregiver selectedCaregiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_caregiver_detail, container, false);

        caregiverName = (TextView) rootView.findViewById(R.id.caregiver_name);
        caregiverAge = (TextView) rootView.findViewById(R.id.caregiver_age);
        caregiverImg = (ImageView) rootView.findViewById(R.id.caregiver_img);
        contractButton = (AppCompatButton) rootView.findViewById(R.id.contract_btn);
        caregiverEmail = (TextView) rootView.findViewById(R.id.email_text);
        caregiverPhone = (TextView) rootView.findViewById(R.id.fone_text);

        FloatingActionButton backBtn = (FloatingActionButton) rootView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, AppService.getPreviousFragment());
                fragmentTransaction.commit();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.comments){
                    Toast.makeText(getContext(), "Comentários", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Avaliações", Toast.LENGTH_SHORT).show();
                }
            }
        };

        layoutComments = (RelativeLayout) rootView.findViewById(R.id.comments);
        layoutRating = (RelativeLayout) rootView.findViewById(R.id.rating);
        layoutComments.setOnClickListener(listener);
        layoutRating.setOnClickListener(listener);

        selectedCaregiver = AppService.getSelectedCaregiver();

        caregiverName.setText(selectedCaregiver.getName());
        caregiverAge.setText(String.valueOf(selectedCaregiver.getAge()) + " anos");
        caregiverEmail.setText("E-mail: " + selectedCaregiver.getEmail());
        caregiverPhone.setText("Fone: " + selectedCaregiver.getTelephone());

        contractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contract contract = new Contract(selectedCaregiver, new User("Gabriel", "Gabrielg@gmail.com"));
                selectedCaregiver.setContract(contract);
                AppService.setContractedCaregiver(selectedCaregiver);

                Toast.makeText(getContext(), "Contratou o " + selectedCaregiver.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }
}
