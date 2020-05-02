package com.example.getaservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class ForgetAndChangePasswordActivity extends AppCompatActivity {

    private EditText edtMode;
    private TextView txtMode;
    private Button submit;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressBar1;
    private TextInputLayout labelMode;



    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_and_change_password);
        submit=findViewById(R.id.submit_button);
        edtMode=findViewById(R.id.mode);
        //progressBar1 = findViewById(R.id.progressBar1);
        firebaseAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isEmailValid(EmailTxt.getText().toString())){
//                    Toast.makeText(ForgotPasswordActivity.this,"please enter valid email Id",Toast.LENGTH_SHORT).show();
//                }
                //progressBar1.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(edtMode.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetAndChangePasswordActivity.this,"reset link sent to your registered email",Toast.LENGTH_SHORT).show();
                            //progressBar1.setVisibility(View.GONE);
                            Intent intent2 = new Intent(ForgetAndChangePasswordActivity.this,LoginActivity.class);
                            startActivity(intent2);
                        }
                        else {
                            Toast.makeText(ForgetAndChangePasswordActivity.this,"entered email is not registered",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}