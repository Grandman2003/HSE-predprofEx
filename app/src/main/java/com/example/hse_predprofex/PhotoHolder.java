package com.example.hse_predprofex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PhotoHolder extends RecyclerView.ViewHolder {
    ImageView photo_frame;
    private final int REQUEST_CODE = 1234;
    private LinearLayout frame;
    private Uri imager;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);
        photo_frame = itemView.findViewById(R.id.add_photo);
        frame = itemView.findViewById(R.id.frame);
    }

    public Uri getImager() {
        return imager;
    }

    public void setImager(Uri imager) {
        this.imager = imager;
    }

    public void onBindModel(Context context, Uri image_data, int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("photo_change", "change it");
                context.startActivity(intent);
            }
        });
        if (image_data != null) {
            photo_frame.setImageURI(image_data);
            if (position!=0) {
                try {
                    File f=new File(context.getExternalCacheDir()+"/"+"myfile");
                    FileOutputStream stream=new FileOutputStream(f);
                     Pair<Integer, Integer> resolution = config_res(position);
                    Bitmap picture = Bitmap.createScaledBitmap(drawableToBitmap(photo_frame.getDrawable()),
                            resolution.first, resolution.second, false);
                    //Log.v("TAGYYY",String.valueOf(picture_effects.getConfig()));
                    photo_frame.setImageBitmap(picture);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
            imager = image_data;
            frame.setBackgroundColor(context.getColor(R.color.transparent));
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static Pair<Integer,Integer> config_res(int position){
        switch (position){
            case 1:
                return new Pair<>(1080,1920);
            case 2:
                return new Pair<>(1600,1200);
            case 3:
                return new Pair<>(1800,900);
        }
        return null;
    }
}
