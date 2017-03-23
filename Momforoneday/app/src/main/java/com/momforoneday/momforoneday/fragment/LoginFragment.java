package com.momforoneday.momforoneday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.service.AppService;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class LoginFragment extends Fragment {

    private View rootView;
    private TextView nameTextView;
    private TextView sloganTextView;
    private TextView registerTextView;
    private ImageView logoImageView;
    private AppCompatButton loginButton;
    private LinearLayout layoutLogin;
    private EditText matriculaLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (AppCompatButton) rootView.findViewById(R.id.login_btn);
        nameTextView = (TextView) rootView.findViewById(R.id.name_textview);
        logoImageView = (ImageView) rootView.findViewById(R.id.logo_img);
        sloganTextView = (TextView) rootView.findViewById(R.id.slogan_textview);
        layoutLogin = (LinearLayout) rootView.findViewById(R.id.layout_login);
        registerTextView = (TextView) rootView.findViewById(R.id.register_textview);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Registre-se", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layoutLogin.getVisibility() != View.VISIBLE) {
                    nameTextView.setVisibility(View.GONE);
                    sloganTextView.setVisibility(View.GONE);

                    logoImageView.animate().scaleX(0.8f).scaleY(0.8f).y(300f).start();

                    layoutLogin.animate().scaleX(1f).scaleY(1f).y(logoImageView.getY() + 80f).start();
                    layoutLogin.setVisibility(View.VISIBLE);
                } else {

                    AppService.setCurrentUser("Gabriel Guimarães");

                    getActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.gradient_view).setVisibility(View.VISIBLE);

                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new HomeFragment());
                    fragmentTransaction.commit();
                }

            }
        });

        return rootView;
    }
}
