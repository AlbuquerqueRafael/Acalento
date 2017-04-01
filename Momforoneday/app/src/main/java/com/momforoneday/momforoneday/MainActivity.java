package com.momforoneday.momforoneday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractRequestFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.fragment.LoginFragment;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.util.CircleTransform;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar;
    private BottomNavigationView navigationCaregiverBar;
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

                    if (AppService.getContractedCaregiver() != null) {
                        fragmentTransaction =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, new ContractsFragment());
                        fragmentTransaction.commit();
                        return true;
                    } else {
                        Toast.makeText(MainActivity.this, "Você não possui nenhum contrato no momento", Toast.LENGTH_SHORT).show();
                        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigationBar.getMenu().findItem(R.id.navigation_home));
                        return false;
                    }

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationCaregiverItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentTransaction fragmentTransaction;

            switch (item.getItemId()) {

                case R.id.navigation_active_contract:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new ContractListFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.navigation_requests:

                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new ContractRequestFragment());
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

        if (!isAuthenticated()){
            navigationCaregiverBar = (BottomNavigationView) findViewById(R.id.navigation_caregiver);
            navigationCaregiverBar.setOnNavigationItemSelectedListener(mOnNavigationCaregiverItemSelectedListener);

            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigationCaregiverBar.getMenu().findItem(R.id.navigation_active_contract));
            navigationCaregiverBar.setVisibility(View.VISIBLE);

        } else {
            navigationCaregiverBar.setVisibility(View.GONE);
            navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigationBar.getMenu().findItem(R.id.navigation_home));

            navigationBar = (BottomNavigationView) findViewById(R.id.navigation);
            navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigationBar.setVisibility(View.VISIBLE);
        }

        gradientView = findViewById(R.id.gradient_view);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.foto_caregiver);
        CircleTransform transform = new CircleTransform();
        Bitmap image = transform.transform(bm);

        AppService.setImage(image);



    }

    private boolean isAuthenticated(){
        return false;
    }

}
