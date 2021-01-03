package com.example.hse_predprofex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.StrictMode;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    //it was commented
    ViewPager2 pager;
    PhotoPagesAdapter adapter;
    FloatingActionButton fab;
    private static final int GALLERY_REQUEST_CODE = 1234;
    private static final int SHARE_REQUEST_CODE=8888;
    private int CUR_IT=0;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        tabs=findViewById(R.id.tabs_points);
        tabs.setVisibility(View.GONE);
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {
                        pager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        pager=findViewById(R.id.photos);
        adapter=new PhotoPagesAdapter(this);
        pager.setAdapter(adapter);
        pager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                tabs.selectTab(tabs.getTabAt(pager.getCurrentItem()));
                CUR_IT=pager.getCurrentItem();
               // Toast.makeText(page.getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });

        if(getIntent().getStringExtra("photo_change")!=null
                && getIntent().getStringExtra("photo_change").equals("change it")){
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Pick an image"), GALLERY_REQUEST_CODE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSend();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //Toast.makeText(this,"WAAAAAAA",Toast.LENGTH_SHORT).show();
            Uri image_data = data.getData();
            adapter.setImagy(image_data);
            tabs.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            adapter.photoholders.get(0).onBindModel(adapter.context, image_data, 0);
        }
    }

    private void ImageSend(){
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        PhotoPagesAdapter adapter= (PhotoPagesAdapter) pager.getAdapter();
        BitmapDrawable picture= (BitmapDrawable) adapter.photoholders
                .get(pager.getCurrentItem()).photo_frame.getDrawable();
        Bitmap bitmap=picture.getBitmap();
        File file=new File(getExternalCacheDir()
                +"/"+getResources().getString(R.string.app_name)+".png");
        Intent shareint;
        try {
            FileOutputStream stream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
            shareint=new Intent(Intent.ACTION_SEND);
            shareint.setType("image/*");
            shareint.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"share image"));
    }

}