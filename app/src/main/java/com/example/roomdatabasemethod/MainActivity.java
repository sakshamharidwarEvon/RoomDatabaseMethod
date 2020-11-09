package com.example.roomdatabasemethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;
    private static ContactDatabase instance;
    String message,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ui", String.valueOf(getIntent().getExtras()));
        Log.d("Token", FirebaseInstanceId.getInstance().getToken());

        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.example.roomdatabasemethod_FCM-MESSAGE"));
        if(getIntent().getExtras()!=null){
            for(String key:getIntent().getExtras().keySet()){
                Log.d("uo", String.valueOf(getIntent().getExtras().keySet()));
                if(key.equals("title")){
                    Log.d("uh",getIntent().getExtras().getString("title").toString());
                     title=(getIntent().getExtras().getString("title"));}
                else
                if(key.equals("message")){
                    Log.d("uj",getIntent().getExtras().getString("message").toString());
                    message=(getIntent().getExtras().getString("message"));

                }
            }
        }

        contactViewModel.insert(new Contact(title,message));
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter=new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel= ViewModelProviders.of(this).get(ContactViewModel.class) ;
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {

                adapter.setContacts(contacts);
            }
        });
    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title=intent.getStringExtra("title");
            String message=intent.getStringExtra("message");

            Log.d("vi",title);
            Log.d("ve",message);

        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }
}