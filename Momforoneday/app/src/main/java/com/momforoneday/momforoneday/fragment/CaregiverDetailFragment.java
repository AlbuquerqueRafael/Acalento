package com.momforoneday.momforoneday.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class CaregiverDetailFragment extends Fragment {

    private View rootView;
    private TextView caregiverName;
    private ImageView caregiverImg;
    private TextView contractButton;
    private TextView ratingButton;
    private TextView caregiverEmail;
    private TextView caregiverPhone;
    private TextView caregiverCourse;
    private NavigationTabStrip mNavigationTabStrip;
    private ViewPager mViewPager;
    private BottomNavigationView navigationBar;
    private View gradientView;

    private Caregiver selectedCaregiver;

    private List<Integer> segunda;
    private List<Integer> terça;
    private List<Integer> quarta;
    private List<Integer> quinta;
    private List<Integer> sexta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_caregiver_detail, container, false);

        initUI();
        setUI();

        return rootView;
    }

    private void initUI(){
        caregiverName = (TextView) rootView.findViewById(R.id.caregiver_name);
        caregiverImg = (ImageView) rootView.findViewById(R.id.caregiver_img);
        caregiverEmail = (TextView) rootView.findViewById(R.id.caregiver_email);
        caregiverPhone = (TextView) rootView.findViewById(R.id.caregiver_phone);
        contractButton = (TextView) rootView.findViewById(R.id.contract_button);
        caregiverCourse = (TextView) rootView.findViewById(R.id.caregiver_course);
        mViewPager = (ViewPager) rootView.findViewById(R.id.vp);

        navigationBar = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        gradientView = getActivity().findViewById(R.id.gradient_view);

        ratingButton = (TextView) rootView.findViewById(R.id.rate_button);
        mNavigationTabStrip = (NavigationTabStrip) rootView.findViewById(R.id.navigation_tab);
    }

    private void setUI(){

        segunda = new ArrayList<>();
        terça = new ArrayList<>();
        quarta = new ArrayList<>();
        quinta = new ArrayList<>();
        sexta = new ArrayList<>();

        segunda.add(0);segunda.add(5);segunda.add(10);segunda.add(15);

        terça.add(1);terça.add(6);terça.add(11);terça.add(16);

        quarta.add(2);quarta.add(7);quarta.add(12);quarta.add(17);

        quinta.add(3);quinta.add(8);quinta.add(13);quinta.add(18);

        sexta.add(4);sexta.add(9);sexta.add(14);sexta.add(19);

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

        navigationBar.setVisibility(View.GONE);
        gradientView.setVisibility(View.GONE);

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        selectedCaregiver = AppService.getSelectedCaregiver();

        caregiverName.setText(selectedCaregiver.getName());
        caregiverEmail.setText(selectedCaregiver.getEmail());
        caregiverPhone.setText(selectedCaregiver.getTelephone());
        caregiverCourse.setText(selectedCaregiver.getCourse());

        contractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContractDialog();
            }
        });

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }


            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = new View(getContext());
                container.addView(view);
                return view;
            }
        });

        mNavigationTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                android.support.v4.app.FragmentTransaction fragmentTransaction;

                switch (position){
                    case 0:
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, new HoraryFragment());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, new CommentFragment());
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNavigationTabStrip.onPageSelected(0);
        mNavigationTabStrip.setViewPager(mViewPager);

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HoraryFragment());
                fragmentTransaction.commit();

    }

    private void showContractDialog(){
        final Dialog inputDialog = new Dialog(getContext());
        inputDialog.setContentView(R.layout.contract_dialog);

        final RadioGroup radioGroup = (RadioGroup) inputDialog.findViewById(R.id.radio_group);
        final Button okButton = (Button) inputDialog.findViewById(R.id.ok_button);
        final Button cancelButton = (Button) inputDialog.findViewById(R.id.cancel_button);

        List<Integer> horarios = selectedCaregiver.getSchedules();

        for (Integer sc: horarios) {
            String text = getTextFromSchedule(sc);
            RadioButton rb = new RadioButton(getContext());
            rb.setId(sc);
            rb.setText(text);
            radioGroup.addView(rb);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

                contractCaregiver(radioButton.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });

        inputDialog.show();
    }

    private String getTextFromSchedule(int sc) {
        String time = "";
        String day = "";

        if (sc < 5) {
            time = "8h às 10h";
        } else if (sc < 10) {
            time = "10h às 12h";
        } else if (sc < 15) {
            time = "14h às 16h";
        } else {
            time = "16h às 18h";
        }

        if (segunda.contains(sc)) {
            day = "Segunda";
        } else if (terça.contains(sc)) {
            day = "Terça";
        } else if (quarta.contains(sc)) {
            day = "Quarta";
        } else if (quinta.contains(sc)) {
            day = "Quinta";
        } else if (sexta.contains(sc)) {
            day = "Sexta";
        }

        String result = day + ": " + time;

        return result;
    }

    private void contractCaregiver(String schedule){
        User currentUser = AppService.getCurrentUser();
        Contract contract = new Contract(selectedCaregiver.getName(), currentUser, schedule);

        FirebaseController.setContract(contract);

        selectedCaregiver.setContract(contract);
        AppService.setContractedCaregiver(selectedCaregiver);

        Toast.makeText(getContext(), "Contratou " + selectedCaregiver.getName() + " para " + schedule , Toast.LENGTH_SHORT).show();

    }

    private void showRatingDialog(){
        final Dialog inputDialog = new Dialog(getContext());
        inputDialog.setContentView(R.layout.rating_dialog);

        final RatingBar ratingBar = (RatingBar) inputDialog.findViewById(R.id.rating_bar);
        final Button okButton = (Button) inputDialog.findViewById(R.id.ok_button);
        final Button cancelButton = (Button) inputDialog.findViewById(R.id.cancel_button);

        ratingBar.setRating(AppService.getSelectedCaregiver().getRating());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Caregiver care = AppService.getSelectedCaregiver();
                care.setRating((int) Math.floor(ratingBar.getRating()));

                FirebaseController.saveCaregiver(care);

                Toast.makeText(getContext(), "Você avaliou em " + (int) Math.floor(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                inputDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });

        inputDialog.show();
    }
}
