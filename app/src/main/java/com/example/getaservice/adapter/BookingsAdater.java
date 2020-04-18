package com.example.getaservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.BookingModel;
import com.example.getaservice.R;
import com.example.getaservice.Workermodel;

import java.util.ArrayList;

public class BookingsAdater extends RecyclerView.Adapter<BookingsAdater.RecyclerVH> {

    Context c;
    ArrayList<BookingModel> bookingModelArrayList;

    public BookingsAdater(Context c, ArrayList<BookingModel> bookingModelArrayList) {
        this.c = c;
        this.bookingModelArrayList = bookingModelArrayList;
    }

    @Override
    public BookingsAdater.RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.list_item_bookings,parent,false);
        return new BookingsAdater.RecyclerVH(v);
    }


    @Override
    public void onBindViewHolder(BookingsAdater.RecyclerVH holder, int position) {


        holder.nameTxt.setText(bookingModelArrayList.get(position).getCustomer());
        holder.address.setText(bookingModelArrayList.get(position).getAddress());
        holder.bookingday.setText(bookingModelArrayList.get(position).getBookingDay());
        holder.booktime.setText(bookingModelArrayList.get(position).getBookingTime());
        holder.phone.setText(bookingModelArrayList.get(position).getCustomerPhone());
        holder.wname.setText(bookingModelArrayList.get(position).getWorker());

    }

    @Override
    public int getItemCount() {
        return bookingModelArrayList.size();
    }


    /*
    VIEWHOLDER CLASS
     */
    public class RecyclerVH extends RecyclerView.ViewHolder
    {
        TextView nameTxt,bookingday,booktime,wname,phone,address;

        public RecyclerVH(View itemView) {
            super(itemView);

            nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
            bookingday=(TextView) itemView.findViewById(R.id.dayTv);
            booktime=(TextView) itemView.findViewById(R.id.timeTv);

            wname=(TextView) itemView.findViewById(R.id.wnameTxt);
            phone=(TextView) itemView.findViewById(R.id.phoneTv);

            address=(TextView) itemView.findViewById(R.id.addressTv);


        }
    }
}

