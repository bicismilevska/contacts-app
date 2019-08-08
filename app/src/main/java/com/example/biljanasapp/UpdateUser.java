package com.example.biljanasapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateUser extends AppCompatActivity {
    public static final String TAG = "UpdateUser";
    Bundle bundle;
    int position;
    EditText fname;
    EditText lname;
    EditText num;
    Button updatebutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);
        Log.i(TAG,"the position is "+ position);
        fname=findViewById(R.id.ufn);
        lname=findViewById(R.id.uln);
        num=findViewById(R.id.un);
        updatebutton=findViewById(R.id.updatebutton);
        final AppDatabase ad= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"production")
                .allowMainThreadQueries()
                .build();
        position=getIntent().getIntExtra("id",0);
        Log.i(TAG,"the position is "+ position);
        User user=ad.userDao().getUser(position);
        Log.i(TAG,"first name is "+ user.getFirstname());
        fname.setText(user.getFirstname());
        lname.setText(user.getLastname());
        num.setText(user.getNumber());
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first=fname.getText().toString().substring(0,1).toUpperCase()+ fname.getText().toString().substring(1).toLowerCase();
                Log.i(TAG,first);
                String last=lname.getText().toString().substring(0,1).toUpperCase()+ lname.getText().toString().substring(1).toLowerCase();
                Log.i(TAG,first);
                ad.userDao().updateUser(first,last,num.getText().toString(),position);
                startActivity(new Intent(UpdateUser.this,MainActivity.class));
            }
        });

    }

}
