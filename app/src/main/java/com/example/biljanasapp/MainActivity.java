package com.example.biljanasapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    FloatingActionButton button;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ImageButton deletebutton;
    //ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        AppDatabase ad= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"production")
                .allowMainThreadQueries()
                .build();
        List<User> users=ad.userDao().getAllUsers();

        adapter=new UserAdapter(ad,users,this);
        recyclerView.setAdapter(adapter);


        button=findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Button clicked");
                startActivity(new Intent(MainActivity.this,CreateUser.class));
            }
        });


    }


}
