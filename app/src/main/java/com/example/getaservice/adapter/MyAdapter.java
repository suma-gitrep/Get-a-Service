package com.example.getaservice.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.BookingModel;
import com.example.getaservice.CustomerRegisterActivity;
import com.example.getaservice.ExampleDialog;
import com.example.getaservice.LoginActivity;
import com.example.getaservice.R;
import com.example.getaservice.Shared;
import com.example.getaservice.WorkerFullDetailsActivity;
import com.example.getaservice.Workermodel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerVH> {

    Context c;
    String[] spacecrafts;
    ArrayList<Workermodel> workermodelArrayList;


    public MyAdapter(Context c, ArrayList<Workermodel> workermodelArrayList) {
        this.c = c;
        this.workermodelArrayList = workermodelArrayList;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.list_item_worker, parent, false);
        return new RecyclerVH(v);
    }


    @Override
    public void onBindViewHolder(RecyclerVH holder, final int position) {
        holder.nameTxt.setText(workermodelArrayList.get(position).getName());

        holder.category.setText(workermodelArrayList.get(position).getCategory());
        holder.experience.setText(workermodelArrayList.get(position).getExperiencestr());

        holder.charges.setText(workermodelArrayList.get(position).getChargestr());

        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared shared = new Shared(c);
                String isUserExists = shared.getIsUserLoggedIn();
                if (isUserExists.equalsIgnoreCase("true")){
                    openDialog(position);
                }
                else{
                    Intent ob = new Intent(c, LoginActivity.class);
                    c.startActivity(ob);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return workermodelArrayList.size();
    }


    /*
    VIEWHOLDER CLASS
     */
    public class RecyclerVH extends RecyclerView.ViewHolder {
        TextView nameTxt,category,charges,experience;
        ImageView profilepic;
        Button bookBtn;


        public RecyclerVH(View itemView) {
            super(itemView);
            bookBtn=(Button)itemView.findViewById(R.id.bookBtn);
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);
category=(TextView) itemView.findViewById(R.id.categoryTV);
            charges=(TextView) itemView.findViewById(R.id.chargesTV);
            experience=(TextView) itemView.findViewById(R.id.expeTV);



        }
    }


    private void openDialog(final int position) {
        final Dialog bookingDialog = new Dialog(c);
        bookingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bookingDialog.setContentView(R.layout.layout_dialog);
        bookingDialog.setCanceledOnTouchOutside(false);
        bookingDialog.setCancelable(true);
       final EditText dayEdittext = bookingDialog.findViewById(R.id.dayEdittext);
       final EditText timeEdittext = bookingDialog.findViewById(R.id.timeEdittext);
       TextView saveTextView = bookingDialog.findViewById(R.id.saveTextView);

       saveTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (dayEdittext.getText().toString().length()>0&&timeEdittext.getText().toString().length()>0){
                    Workermodel customerDetails = new Workermodel();
                    Shared shared = new Shared(c);
                   String userDetails = shared.getWorkerModel();
                   customerDetails = new Gson().fromJson(userDetails, Workermodel.class);

                   BookingModel bookingModel = new BookingModel();
                   bookingModel.setCustomer(customerDetails.getName());
                   bookingModel.setWorker(workermodelArrayList.get(position).getName());
                   bookingModel.setCustomer(customerDetails.getName());
                   bookingModel.setCustomerEmail(customerDetails.getEmail());
                   bookingModel.setWorkerEmail(workermodelArrayList.get(position).getEmail());
                   bookingModel.setCustomerPhone(customerDetails.getPhonestr());
                   bookingModel.setAddress(customerDetails.getAddressstr());
                   bookingModel.setWorkerPhone(workermodelArrayList.get(position).getPhonestr());
                   bookingModel.setBookingDay(dayEdittext.getText().toString().trim());
                   bookingModel.setBookingTime(timeEdittext.getText().toString());

                   FirebaseDatabase rootNode= FirebaseDatabase.getInstance();
                   DatabaseReference reference=rootNode.getReference("Bookings");
                   reference.push().setValue(bookingModel);
                   bookingDialog.dismiss();
               }
               else{
                   Toast.makeText(c, "Please enter valid details", Toast.LENGTH_SHORT).show();
               }
           }
       });


        bookingDialog.show();

    }

}

