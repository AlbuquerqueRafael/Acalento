package com.momforoneday.momforoneday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.fragment.ConfigFragment;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.fragment.HomeFragment;
import com.momforoneday.momforoneday.fragment.LoginFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar;
    private View gradientView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentTransaction fragmentTransaction;

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new ContractListFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.navigation_contracts:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new ContractsFragment());
                    fragmentTransaction.commit();
                    return true;

               /*case R.id.navigation_config:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new ConfigFragment());
                    fragmentTransaction.commit();
                    return true;*/
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        navigationBar = (BottomNavigationView) findViewById(R.id.navigation);
        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigationBar.getMenu().findItem(R.id.navigation_home));
        gradientView = findViewById(R.id.gradient_view);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.foto_caregiver);
        CircleTransform transform = new CircleTransform();
        Bitmap image = transform.transform(bm);

        AppService.setImage(image);

        if (!isAuthenticated()){
            navigationBar.setVisibility(View.GONE);
            gradientView.setVisibility(View.GONE);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, new LoginFragment());
            fragmentTransaction.commit();
        }

    }

    private boolean isAuthenticated(){
        return true;
    }

}
