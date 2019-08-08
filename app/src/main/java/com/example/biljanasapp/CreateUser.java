package com.example.biljanasapp;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CreateUser extends AppCompatActivity {
    public static final String TAG="CreateUser";
   EditText firstname;
   EditText lastname;
   EditText number;
   Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        firstname=findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        number=findViewById(R.id.number);
        but=findViewById(R.id.add);
       final AppDatabase ad= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"production")
                .allowMainThreadQueries()
                .build();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"contact added"+ number.getText().toString());
                if(firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || number.getText().toString().isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateUser.this).create();
                    alertDialog.setTitle("Incomplete information");
                    alertDialog.setMessage("All three fields need to be completed!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
                else if(!(number.getText().toString().matches("[0-9]+"))){
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateUser.this).create();
                    alertDialog.setTitle("Number Invalid");
                    alertDialog.setMessage("Number should be in numeric form ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
                else {
                    String first=firstname.getText().toString().substring(0,1).toUpperCase()+ firstname.getText().toString().substring(1).toLowerCase();
                    Log.i(TAG,first);
                    String last=lastname.getText().toString().substring(0,1).toUpperCase()+ lastname.getText().toString().substring(1).toLowerCase();
                    Log.i(TAG,first);
                    User u = new User(first, last,number.getText().toString());
                    ad.userDao().insertAll(u);


                    startActivity(new Intent(CreateUser.this, MainActivity.class));
                }
            }
        });


    }
    public boolean isNumber(String num){
        for (int i = 0; i <num.length() ; i++) {
            if(!(isNumber(num.substring(i,i+1))))
                return false;
        }
        return true;
    }
}
