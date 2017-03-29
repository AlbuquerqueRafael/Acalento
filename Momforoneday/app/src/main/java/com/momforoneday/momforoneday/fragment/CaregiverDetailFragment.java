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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.controller.FirebaseController;
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

        contractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contract contract = new Contract(selectedCaregiver, new User("Gabriel", "Gabrielg@gmail.com"));
                selectedCaregiver.setContract(contract);
                AppService.setContractedCaregiver(selectedCaregiver);

                Toast.makeText(getContext(), "Contratou o " + selectedCaregiver.getName(), Toast.LENGTH_SHORT).show();

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

                FirebaseController.updateCaregiver(care);

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
