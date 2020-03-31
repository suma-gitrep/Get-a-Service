package com.example.getaservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity  {

    Button save,cancel;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        save=findViewById(R.id.savebutton);
        cancel=findViewById(R.id.cancel);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(EditActivity.this, "Succesfully saved all the data of user", Toast.LENGTH_LONG).show();

                Intent in=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        EditActivity.this,
                        "Cancelled to save the data in edit profile",
                        Toast.LENGTH_LONG).show();
                Intent in=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);

            }
        });
    }

    }
