package com.example.hse_predprofex;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;

public class PhotoPagesAdapter extends RecyclerView.Adapter<PhotoHolder> {
    Context context;
    Uri imagy;
    ArrayList<PhotoHolder> photoholders;

    public PhotoPagesAdapter(Context context) {
        this.context = context;
        photoholders=new ArrayList<PhotoHolder>();
    }

    public void setImagy(Uri imagy) {
        this.imagy = imagy;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderlay= (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.photo_variation,parent,false);
        photoholders.add(new PhotoHolder(holderlay));
        return photoholders.get(photoholders.size()-1);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.onBindModel(context,imagy,position);
    }

    @Override
    public int getItemCount() {
        if(imagy==null){
            return 1;
        }
        return 4;
    }
}
