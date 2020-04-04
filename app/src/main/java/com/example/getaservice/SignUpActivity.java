package com.example.getaservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Button regWorker,regCustomer;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_signup_details);

        regCustomer=(Button)findViewById(R.id.regcustomer);
        regWorker=(Button)findViewById(R.id.regworker);


        regCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this, CustomerRegisterActivity.class);
                startActivity(intent);
                // finish();


            }
        });
        regWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SignUpActivity.this, WorkerRegistrationActivity.class);
                startActivity(intent);
                //finish();


            }
        });






    }

}
