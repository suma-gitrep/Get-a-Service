package com.example.getaservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class CustomerEditActivity extends AppCompatActivity {

    Shared shared;
    private EditText inputEmail, inputPassword,confrimPassword,username,phoneNumber,addressDetails;
Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customereditprofile);
        shared = new Shared(CustomerEditActivity.this);
save=(Button)findViewById(R.id.save_button);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        confrimPassword=(EditText)findViewById(R.id.confirmpassword);
        username=(EditText)findViewById(R.id.userName);
        addressDetails=(EditText)findViewById(R.id.address);
        phoneNumber=(EditText)findViewById(R.id.phone);

        Workermodel workermodel = new Gson().fromJson(shared.getWorkerModel(), Workermodel.class);

        final String  name=workermodel.getName();
        inputEmail.setText(workermodel.getEmail());
        inputPassword.setText(workermodel.getPasswordstr());
        confrimPassword.setText(workermodel.getPasswordstr());
        username.setText(workermodel.getName());
        addressDetails.setText(workermodel.getAddressstr());
        phoneNumber.setText(workermodel.getPhonestr());

save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(name);
        Workermodel workermodel = new Workermodel(username.getText().toString(),inputPassword.getText().toString(),inputEmail.getText().toString(),phoneNumber.getText().toString(),"","","",addressDetails.getText().toString(),"","","customer");

        //updating artist
        dR.setValue(workermodel);
        Toast.makeText(getApplicationContext(), "details Updated", Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        shared.setWorkerModel(gson.toJson(workermodel));
        shared.setIsUserLoggedIn("true");
finish();
    }
});



    }
}