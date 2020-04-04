package com.example.getaservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,confrimPassword,username,phoneNumber,addressDetails;
    private FirebaseAuth auth;
    private Button btnSignUp;
    private ProgressDialog PD;
    private TextView  btnLogin;
    SharedPreferences pref;
    SharedPreferences.Editor editor;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(CustomerRegisterActivity.this, MainActivity.class));
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        confrimPassword=(EditText)findViewById(R.id.confirmpassword);
        username=(EditText)findViewById(R.id.userName);
        addressDetails=(EditText)findViewById(R.id.address);
        phoneNumber=(EditText)findViewById(R.id.phone);


        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnLogin = (TextView) findViewById(R.id.sign_in_button);



        pref = getSharedPreferences("Registration", 0);
        // get editor to edit in file
        editor = pref.edit();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                final String emailstr = inputEmail.getText().toString();
                final String passwordstr = inputPassword.getText().toString();
                final String usernamestr= username.getText().toString();
                final String confrimpasswordstr=confrimPassword.getText().toString();
                final String phonestr=phoneNumber.getText().toString();
                final String addressstr=addressDetails.getText().toString();

                if(inputEmail.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }

                else if( inputPassword.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if( username.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if( confrimPassword.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter confrim password", Toast.LENGTH_SHORT).show();
                }
                else if( phoneNumber.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }

                else if( addressDetails.getText().length()<=0){
                    Toast.makeText(CustomerRegisterActivity.this, "Enter address details", Toast.LENGTH_SHORT).show();
                }
                else{
                    editor.putString("Name", usernamestr);
                    editor.putString("Email",emailstr);
                    editor.putString("Password",passwordstr);
                    editor.putString("confirmpass",confrimpasswordstr);
                    editor.putString("address",addressstr);
                    editor.putString("phone",phonestr);
                    editor.putString("usertype","customer");
                    editor.commit();   // commit the values

                    // after saving the value open next activity
                    Intent ob = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
                    startActivity(ob);
                }
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override            public void onClick(View view) {
                        finish();
                    }
                });
            }
        });




    }
}

