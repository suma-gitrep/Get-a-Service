package com.example.getaservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.R;

public class BookingsAdater extends RecyclerView.Adapter<BookingsAdater.RecyclerVH> {

    Context c;
    String[] spacecrafts;

    public BookingsAdater(Context c, String[] spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public BookingsAdater.RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.list_item_bookings,parent,false);
        return new BookingsAdater.RecyclerVH(v);
    }


    @Override
    public void onBindViewHolder(BookingsAdater.RecyclerVH holder, int position) {
        holder.nameTxt.setText(spacecrafts[position]);
    }

    @Override
    public int getItemCount() {
        return spacecrafts.length;
    }


    /*
    VIEWHOLDER CLASS
     */
    public class RecyclerVH extends RecyclerView.ViewHolder
    {
        TextView nameTxt;

        public RecyclerVH(View itemView) {
            super(itemView);

            nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        }
    }
}

