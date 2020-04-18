package com.example.getaservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.List;

public class WorkerRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button sign_up_button;

EditText username,password,email,phonenumber,experience,certification,charges,address;
    Switch availability;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_worker);


        sign_up_button=(Button)findViewById(R.id.sign_up_button) ;
availability=  (Switch) findViewById(R.id.switch1);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select A Category");
        categories.add("Plumber");
        categories.add("Carpenter");
        categories.add("Electrician");
        categories.add("cleaner");
        categories.add("food services");
        categories.add("Baby Sitter");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        username=(EditText)findViewById(R.id.userName);
        address=(EditText)findViewById(R.id.address);
        phonenumber=(EditText)findViewById(R.id.phone);
charges=(EditText)findViewById(R.id.charges);

        experience = (EditText) findViewById(R.id.experience);
        certification = (EditText) findViewById(R.id.certifications);



        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String usernamestr= username.getText().toString();
                final String passwordstr = password.getText().toString();


                final String emailstr = email.getText().toString();
                final String phonestr=phonenumber.getText().toString();

                final String experiencestr=experience.getText().toString();
                final String certificationstr=certification.getText().toString();
                final String chargestr=charges.getText().toString();

                final String addressstr=address.getText().toString();

                final String category = spinner.getSelectedItem().toString();
                final String status;

                if (availability.isChecked())
                    status = "yes";
                else
                    status ="no";



                if(email.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if( password.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if( username.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else if( charges.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter charges password", Toast.LENGTH_SHORT).show();
                }
                else if( phonenumber.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if( certification.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if( experience.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }

                else if( address.getText().length()<=0){
                    Toast.makeText(WorkerRegistrationActivity.this, "Enter address details", Toast.LENGTH_SHORT).show();
                }

                else{


                    DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkUser = refer.orderByChild("name").equalTo(usernamestr);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(WorkerRegistrationActivity.this, "username already exists", Toast.LENGTH_LONG).show();


                            } else {
                                rootNode= FirebaseDatabase.getInstance();
                                reference=rootNode.getReference("Users");
                                Workermodel worke= new Workermodel(usernamestr, passwordstr, emailstr,  phonestr,  experiencestr,  certificationstr,  chargestr,  addressstr,  category,  status, "worker") ;
                                reference.child(usernamestr).setValue(worke);

                                    Intent ob = new Intent(WorkerRegistrationActivity.this, LoginActivity.class);
                                    startActivity(ob);
                                    finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });




                }



            }
        });
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
