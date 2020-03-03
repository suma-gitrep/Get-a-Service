package com.example.getaservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.getaservice.LoginActivity;
import com.example.getaservice.R;
import com.example.getaservice.WorkerFullDetailsActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerVH> {

    Context c;
    String[] spacecrafts;

    public MyAdapter(Context c, String[] spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.list_item_worker,parent,false);
        return new RecyclerVH(v);
    }


    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {
        holder.nameTxt.setText(spacecrafts[position]);

holder.profilepic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent in=new Intent(c, WorkerFullDetailsActivity.class);
        c.startActivity(in);

    }
});

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
        ImageView profilepic;

        public RecyclerVH(View itemView) {
            super(itemView);

            nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
            profilepic=(ImageView) itemView.findViewById(R.id.profilepic);


        }
    }

}

