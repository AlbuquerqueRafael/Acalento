package com.momforoneday.momforoneday.service;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Comment;
import com.momforoneday.momforoneday.model.Notification;
import com.momforoneday.momforoneday.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class AppService {

    private static Caregiver selectedCaregiver;
    private static Caregiver contractedCaregiver;
    private static Fragment previousFragment;
    private static List<Caregiver> caregiversList = new ArrayList<>();
    private static Bitmap image;
    private static User currentUser;
    private static String lastRequestedPhoto;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void setSelectedCaregiver(Caregiver caregiver){
        selectedCaregiver = caregiver;
    }

    public static Caregiver getSelectedCaregiver() {
        return selectedCaregiver;
    }

    public static void setContractedCaregiver(Caregiver caregiver){
        contractedCaregiver = caregiver;
    }

    public static Caregiver getContractedCaregiver() {
        return contractedCaregiver;
    }

    public static void setPreviousFragment(Fragment fragment) {
        previousFragment = fragment;
    }

    public static Fragment getPreviousFragment(){
        return previousFragment;
    }

    public static void setCaregiversList(List<Caregiver> caregiversList) {
        AppService.caregiversList = caregiversList;
    }

    public static List<Caregiver> getCaregiversList() {
        return caregiversList;
    }

    public static void setImage(Bitmap img) {
        image = img;
    }

    public static Bitmap getImage() {
        return image;
    }

    public static User getCurrentUser() {
        currentUser = new User("Gabriel Guimarães", "gabrielguimaraes@gmail.com");
        return currentUser;
    }

    public static List<Notification> getCregiverNotification(){
        List<Notification> notifications = new ArrayList<>();

        Notification notification = new Notification("Carla, manda uma foto para do joão para mim",
                "Claudia Melina", new User("Carla Ferreira", "carlaferreira@gmail.com"));
        Notification notification1 = new Notification("Carla, como está meu bebê?",
                "Claudia Melina", new User("Carla Ferreira", "carlaferreira@gmail.com"));
        Notification notification2 = new Notification("Carla, manda outra foto, por favor",
                "Claudia Melina", new User("Carla Ferreira", "carlaferreira@gmail.com"));

        notifications.add(notification);
        notifications.add(notification1);
        notifications.add(notification2);

        return notifications;

    }

    public static void setLastRequestedPhoto(String url) {
        lastRequestedPhoto = url;
    }

    public static String getLastRequestedPhoto(){
        return lastRequestedPhoto;
    }

    public static void sendNotification(final String reg_token,  final String title, final String body) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json=new JSONObject();
                    JSONObject dataJson=new JSONObject();
                    dataJson.put("body",body);
                    dataJson.put("title",title);
                    json.put("notification",dataJson);
                    json.put("to", "fZxdu_X5yP0:APA91bHq1elfi1_b3ffHEyGN4h4WCahNa0E_JWbfwnhKvsgxn2dltWBTF88NeClFhYJDKCnWZX573YiBDTLwVdtCDuoIsFn81lcmnKNwDSp2-UdEtevyCHIqGpSJo3k0rk-w8GYA8qqa");
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization","key="+
                                    "AAAA1uOVyN8:APA91bHukkQ2HOaE0JPYPRn4E7vv7IDJfExm2QrGmu3HDVKQSCcRq_UvzO2srQHZ6iNDzlP8a0i7_uVnLkxOZbgCaLdpZpVUd8W92R7Jl0IB6lMabwf26l6i8i5JhQtxUIeJPHUt_FZj")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                }catch (Exception e){
                    System.out.println("error");
                    //Log.d(TAG,e+"");
                }
                return null;
            }
        }.execute();

    }

}
