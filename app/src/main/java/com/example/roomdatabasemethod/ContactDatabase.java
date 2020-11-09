package com.example.roomdatabasemethod;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    private static Context activity;

    public abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context){

        activity=context.getApplicationContext();

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,"contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
    {

        private ContactDao contactDao;
        private PopulateDbAsyncTask(ContactDatabase db){
            contactDao=db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {


//            contactDao.insert(new Contact("App Started 1","9999977878"));
//            contactDao.insert(new Contact("App Started 2","7555555999"));

            //fillWithStartingData(activity);
            return null;
        }
    }

    private static void fillWithStartingData(Context context){
        ContactDao dao=getInstance(context).contactDao();

        JSONArray contacts=loadJSONArray(context);

        try{
            for(int i=0;i<contacts.length();i++){
                JSONObject contact=contacts.getJSONObject(i);

                String contactName=contact.getString("name");
                String phoneNumber=contact.getString("phone");

                dao.insert(new Contact(contactName,phoneNumber));
            }
        }catch (JSONException e){

        }
    }

    private static JSONArray loadJSONArray(Context context){
        StringBuilder builder=new StringBuilder();
        InputStream in =context.getResources().openRawResource(R.raw.contact_list);
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));

        String line;

        try {
            while (( line =reader.readLine()) != null){
                builder.append(line);
            }
            JSONObject json=new JSONObject(builder.toString());
            return json.getJSONArray("contacts");

        }catch (IOException | JSONException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
