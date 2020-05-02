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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,confrimPassword,username,phoneNumber,addressDetails;
   //private FirebaseAuth auth;
    private Button btnSignUp;
    private ProgressDialog PD;
    private TextView  btnLogin;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
FirebaseDatabase rootNode;
DatabaseReference reference;
    FirebaseAuth mAuth;
    String userId;

    //FirebaseFirestore firebaseFirestore;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        mAuth = FirebaseAuth.getInstance();


        // auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(CustomerRegisterActivity.this, MainActivity.class));
//            finish();
//        }

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

                 if( username.getText().length()<=0){
                    username.setError("please enter username");
                }

                 else if( inputPassword.getText().length()<=0){
                     inputPassword.setError("please enter password");
                 }


                else if( confrimPassword.getText().length()<=0){
                    confrimPassword.setError("please enter confrimPassword");

                }
                 else if( !(confrimPassword.getText().toString().equals(inputPassword.getText().toString()))){
                     confrimPassword.setError("passwor and  confrimPassword does not match");

                 }
               else if(!(checkEmail(inputEmail.getText().toString())))
                {
                    inputEmail.setError("please enter correct email Id");

                }
                 else if(inputEmail.getText().length()<=0){
                     inputEmail.setError("please enter email");
                 }

                 else if( phoneNumber.getText().length()<=0){
                    phoneNumber.setError("please enter phoneNumber");

                }
                 else if(!(isValidPhone( phoneNumber.getText().toString())) ){
                     phoneNumber.setError("please correct phone Number");

                 }

                else if( addressDetails.getText().length()<=0){
                    addressDetails.setError("please enter addressDetails");

                }

                else{

//                    DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users");
//                    Query checkUser = refer.orderByChild("name").equalTo(usernamestr);
//                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Toast.makeText(CustomerRegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
//
//
//                            } else {
//
//                                rootNode= FirebaseDatabase.getInstance();
//                                reference=rootNode.getReference("Users");
//                               // CustomerModel cust= new CustomerModel(usernamestr,emailstr,passwordstr,confrimpasswordstr,addressstr,phonestr,"customer");
//                               Workermodel workermodel = new Workermodel(usernamestr,passwordstr,emailstr,phonestr,"","","",addressstr,"","","customer");
//                                reference.child(usernamestr).setValue(workermodel);
//
//                                Intent ob = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
//                                startActivity(ob);
//                                finish();
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//
//
//                    });

                     mAuth.createUserWithEmailAndPassword(emailstr,passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()){
                                // progressBar.setVisibility(View.GONE);
                                 Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_SHORT).show();
                                 userId = mAuth.getCurrentUser().getUid();
                                 DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkUser = refer.orderByChild("name").equalTo(usernamestr);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(CustomerRegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();


                            } else {

                                rootNode= FirebaseDatabase.getInstance();
                                reference=rootNode.getReference("Users");
                               // CustomerModel cust= new CustomerModel(usernamestr,emailstr,passwordstr,confrimpasswordstr,addressstr,phonestr,"customer");
                               Workermodel workermodel = new Workermodel(usernamestr,passwordstr,emailstr,phonestr,"","","",addressstr,"","","customer");
                                reference.child(usernamestr).setValue(workermodel);

                                Intent ob = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
                                startActivity(ob);
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });
                                 Intent intent = new Intent(CustomerRegisterActivity.this, LoginActivity.class);
                                 startActivity(intent);
                             }
                             else {
                                // progressBar.setVisibility(View.GONE);
                                 Toast.makeText(CustomerRegisterActivity.this,"could not register",Toast.LENGTH_SHORT).show();
                             }
                         }
                     });


                }




            }
        });





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                finish();
            }
        });


    }
    private boolean isValidPhone(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length() < 6 || phone.length() > 13)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}

