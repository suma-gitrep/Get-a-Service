package com.example.getaservice;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class CustomerEditActivity extends AppCompatActivity {

    Shared shared;
    private EditText inputEmail, inputPassword,confrimPassword,username,phoneNumber,addressDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customereditprofile);
        shared = new Shared(CustomerEditActivity.this);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        confrimPassword=(EditText)findViewById(R.id.confirmpassword);
        username=(EditText)findViewById(R.id.userName);
        addressDetails=(EditText)findViewById(R.id.address);
        phoneNumber=(EditText)findViewById(R.id.phone);

        Workermodel workermodel = new Gson().fromJson(shared.getWorkerModel(), Workermodel.class);

inputEmail.setText(workermodel.getEmail());
        inputPassword.setText(workermodel.getPasswordstr());
        confrimPassword.setText(workermodel.getPasswordstr());
        username.setText(workermodel.getName());
        addressDetails.setText(workermodel.getAddressstr());
        phoneNumber.setText(workermodel.getPhonestr());




    }
}