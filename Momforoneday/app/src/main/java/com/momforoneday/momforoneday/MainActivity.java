package com.momforoneday.momforoneday;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractRequestFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.fragment.NotificationFragment;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.service.ExifUtil;
import com.momforoneday.momforoneday.service.ImageProvider;
import com.momforoneday.momforoneday.util.CircleTransform;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar;
    private BottomNavigationView navigationCaregiverBar;
    private View gradientView;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private static final int MY_PERMISSIONS_REQUEST_GALERY = 1;
    private boolean hasGalleryPermission = false;
    private boolean hasCameraPermission = false;
    private String text = "";
    private String image = "";
    private boolean baba = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentTransaction fragmentTransaction;

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    if(baba){
                        fragmentTransaction.replace(R.id.content, new NotificationFragment());
                    }else{
                        fragmentTransaction.replace(R.id.content, new ContractListFragment());
                    }
                    fragmentTransaction.commit();
                    return true;

                case R.id.navigation_contracts:
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    if(baba){
                        fragmentTransaction.replace(R.id.content, new ContractRequestFragment());
                    }else{
                        fragmentTransaction.replace(R.id.content, new ContractsFragment());
                    }
                    fragmentTransaction.commit();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        cameraPermission();
        galeryPermission();

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
//            fragmentTransaction.replace(R.id.content, new LoginFragment());
            fragmentTransaction.commit();
        }


    }

    private boolean isAuthenticated(){
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NotificationFragment.CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap imageBitmap = null;
                ContentResolver cr = this.getContentResolver();
                try {
                    imageBitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, NotificationFragment.path);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] byteFormat = stream.toByteArray();
                    image= Base64.encodeToString(byteFormat, Base64.DEFAULT);

                    //imageView.setImageBitmap(bitmap);
                    //imageBitmap = ExifUtil.rotateBitmap(imagePath, myBitmap);
                    //  InputStream image_stream = this.getContentResolver().openInputStream(NotificationFragment.path);
                    // imageBitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(NotificationFragment.path));
                } catch (Exception e) {
                    e.printStackTrace();
                }



                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteFormat = stream.toByteArray();
                image= Base64.encodeToString(byteFormat, Base64.NO_WRAP);

               // image = ImageProvider.convert(imageBitmap);
                initInputTextBox();
            }
        }
    }



    private void cameraPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this
                ,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

        public boolean checkCameraPermission(){
            return hasCameraPermission && hasGalleryPermission;
        }

        private void galeryPermission() {
        // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this
                ,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALERY);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                       String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    hasCameraPermission = true;
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_GALERY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    hasCameraPermission = true;
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    // other 'case' lines to check for other
    // permissions this app might request
    private void initInputTextBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comentário: ");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
            builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text = input.getText().toString();


                Notification notification = new Notification("Gabriel, olha a foto do seu filhao",
                        "Carla Ferreira", image);
                FirebaseController.updateUserNotification(notification);
                AppService.sendNotification("gg", "testando", "testando");
            }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
            });

            builder.show();
        }
    }



