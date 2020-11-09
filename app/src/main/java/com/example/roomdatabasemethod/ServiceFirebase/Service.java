package com.example.roomdatabasemethod.ServiceFirebase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.roomdatabasemethod.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class Service extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recent_token= FirebaseInstanceId.getInstance().toString();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(
                getString(R.string.FCM_PREF), Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_PREF),recent_token);
        editor.commit();
    }
}
