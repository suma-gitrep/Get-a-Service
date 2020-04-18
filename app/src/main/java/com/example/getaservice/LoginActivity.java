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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;
    private ProgressDialog PD;
    private TextView btnSignUp;
    SharedPreferences regpref, loginpref, workreg,workerlogin;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        auth = FirebaseAuth.getInstance();
        loginpref = getSharedPreferences("Login", 0);
        workerlogin=getSharedPreferences("workerLogin",0);
      //  editor = loginpref.edit();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (TextView) findViewById(R.id.sign_up_button);
        btnLogin = (Button) findViewById(R.id.sign_in_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
//
//                try {
//
//                    if (password.length() > 0 && email.length() > 0) {
//                        PD.show();
//                        auth.signInWithEmailAndPassword(email, password)
//                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (!task.isSuccessful()) {
//                                            Toast.makeText(
//                                                    LoginActivity.this,
//                                                    "Authentication Failed",
//                                                    Toast.LENGTH_LONG).show();
//                                            Log.v("error", task.getResult().toString());
//                                        } else {
//                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                        PD.dismiss();
//                                    }
//                                });
//                    } else {
//                        Toast.makeText(
//                                LoginActivity.this,
//                                "Fill All Fields",
//                                Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                regpref = getSharedPreferences("Registration", 0);
                // retrieving value from Registration
                // String name = pref.getString("Name", null);
                String emailsh = regpref.getString("Email", null);
                String passwordsh = regpref.getString("Password", null);
                String usernamesh = regpref.getString("Name", null);
                String usertypestr=regpref.getString("usertype",null);
               // Log.d("shared", emailsh);
              //  Log.d("shared", passwordsh);

                workreg=getSharedPreferences("WorkerRegistration", 0);


                String workername=workreg.getString("workerName",null);
                String workerpass=workreg.getString("workerPassword",null);

                String workeremail=workreg.getString("workerEmail",null);
                String utypestr=workreg.getString("wusertype",null);

                if (inputEmail.getText().toString().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();

                } else {

                    if (usertypestr!=null) {
                        if (email.equalsIgnoreCase(emailsh) && password.equalsIgnoreCase(passwordsh)) {

                            editor = loginpref.edit();

                            editor.putString("emaillogin", email);
                            editor.putString("usernameheader", usernamesh);
                            editor.putString("usertypelogin", usertypestr);
                            editor.commit();
                            Toast.makeText(
                                    LoginActivity.this,
                                    "logged in successfully",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //  finish();
                        } else {

                            Toast.makeText(LoginActivity.this, "wrong email and password details", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{

                        editor=workerlogin.edit();
                        editor.putString("workeremaillogin",workeremail);
                        editor.putString("workerusernamelogin",workername);
                        editor.putString("wusertype",utypestr);
                        Toast.makeText(
                                LoginActivity.this,
                                "logged in successfully",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.forget_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetAndChangePasswordActivity.class).putExtra("Mode", 0));

            }
        });

    }

//    @Override    protected void onResume() {
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }
//        super.onResume();
//
//    }






}