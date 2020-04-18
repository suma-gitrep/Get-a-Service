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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

//import com.google.firebase.auth.FirebaseAuth;

//import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
   // private FirebaseAuth auth;
    private Button btnLogin;
    private ProgressDialog PD;
    private TextView btnSignUp;
    private Shared shared;
   // SharedPreferences regpref, loginpref, workreg,workerlogin;
   // SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
shared = new Shared(LoginActivity.this);
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
<<<<<<< Updated upstream
        auth = FirebaseAuth.getInstance();
        loginpref = getSharedPreferences("Login", 0);
        workerlogin=getSharedPreferences("workerLogin",0);
        //  editor = loginpref.edit();
=======
       //auth = FirebaseAuth.getInstance();
      //  loginpref = getSharedPreferences("Login", 0);
      //  workerlogin=getSharedPreferences("workerLogin",0);
      //  editor = loginpref.edit();
>>>>>>> Stashed changes

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (TextView) findViewById(R.id.sign_up_button);
        btnLogin = (Button) findViewById(R.id.sign_in_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes


                if (inputEmail.getText().toString().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().length() <= 0) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();

                } else {



                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkUser = reference.orderByChild("name").equalTo(email);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                String passwordFromDB = dataSnapshot.child(email).child("passwordstr").getValue(String.class);
                                String usertype=dataSnapshot.child(email).child("usertype").getValue(String.class);



                                if (passwordFromDB.equals(password)) {

                                    Workermodel workermodel = new Workermodel();
                                    workermodel.setEmail(dataSnapshot.child(email).child("email").getValue(String.class));
                                    workermodel.setName(dataSnapshot.child(email).child("name").getValue(String.class));
                                    workermodel.setPasswordstr(dataSnapshot.child(email).child("passwordstr").getValue(String.class));
                                    workermodel.setPhonestr(dataSnapshot.child(email).child("phonestr").getValue(String.class));
                                    workermodel.setCategory(dataSnapshot.child(email).child("category").getValue(String.class));
                                    workermodel.setExperiencestr(dataSnapshot.child(email).child("experiencestr").getValue(String.class));
                                    workermodel.setCertificationstr(dataSnapshot.child(email).child("certificationstr").getValue(String.class));
                                    workermodel.setStatus(dataSnapshot.child(email).child("status").getValue(String.class));
                                    workermodel.setAddressstr(dataSnapshot.child(email).child("addressstr").getValue(String.class));
                                    workermodel.setChargestr(dataSnapshot.child(email).child("chargestr").getValue(String.class));
                                    workermodel.setUsertype(dataSnapshot.child(email).child("usertype").getValue(String.class));
                                    Gson gson = new Gson();
                                    shared.setWorkerModel(gson.toJson(workermodel));
                                    shared.setIsUserLoggedIn("true");
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                /*    if(usertype.equals("worker")){





                                        String wemailDb = ;
                                        String wusernameDb =dataSnapshot.child(email).child("name").getValue(String.class);
                                        String wpasswordDb = );
                                        String wphoneDb =dataSnapshot.child(email).child("phonestr").getValue(String.class);
                                        String wcategoryDB = ;
                                        String wexpDb =dataSnapshot.child(email).child("experiencestr").getValue(String.class);
                                        String wcertiDB = dataSnapshot.child(email).child("certificationstr").getValue(String.class);
                                        String wstatusDb =dataSnapshot.child(email).child("status").getValue(String.class);
                                        String waddressDb = dataSnapshot.child(email).child("addressstr").getValue(String.class);
                                        String wchargesDb =dataSnapshot.child(email).child("chargestr").getValue(String.class);
                                         usertype =dataSnapshot.child(email).child("usertype").getValue(String.class);



                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("name",wusernameDb);
                                        intent.putExtra("email",wemailDb);
                                        intent.putExtra("password",wpasswordDb);
                                        intent.putExtra("phone",wphoneDb);
                                        intent.putExtra("certification",wcertiDB);
                                        intent.putExtra("address",waddressDb);
                                        intent.putExtra("usertype",usertype);
                                        intent.putExtra("category",wcategoryDB);
                                        intent.putExtra("experience",wexpDb);
                                        intent.putExtra("charges",wchargesDb);
                                        intent.putExtra("status",wstatusDb);


                                        startActivity(intent);

                                    }*/
//                                    else if(usertype.equals("customer")){
//                                        String cusernameDb=dataSnapshot.child(email).child("name").getValue(String.class);
//                                        String cemailDb = dataSnapshot.child(email).child("email").getValue(String.class);
//                                        String cpasswordDb=dataSnapshot.child(email).child("passwordstr").getValue(String.class);
//                                        String cphone=dataSnapshot.child(email).child("phonestr").getValue(String.class);
//                                         usertype=dataSnapshot.child(email).child("usertype").getValue(String.class);
//                                        String cconfirmDb=dataSnapshot.child(email).child("confirmpasswordstr").getValue(String.class);
//                                        String caddressDb=dataSnapshot.child(email).child("usertype").getValue(String.class);
//
//
//
//                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                                        intent.putExtra("name",cusernameDb);
//                                        intent.putExtra("email",cemailDb);
//                                        intent.putExtra("password",cpasswordDb);
//                                        intent.putExtra("phone",cphone);
//                                        intent.putExtra("confirm",cconfirmDb);
//                                        intent.putExtra("address",caddressDb);
//                                        intent.putExtra("usertype",usertype);
//
//
//                                        startActivity(intent);
//                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid details", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "User not exists with provided details", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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