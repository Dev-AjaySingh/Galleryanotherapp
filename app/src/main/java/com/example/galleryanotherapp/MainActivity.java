package com.example.galleryanotherapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.os.Environment.MEDIA_MOUNTED;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static int PERMISSION_REQUEST_CODE=100;
    RecyclerView recycler;
    ArrayList<String> images;
    GalleryAdapter adapter;
    GridLayoutManager manager;
    TextView totalimages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recycler=findViewById(R.id.gallery_recycler);
        images=new ArrayList<>();
        adapter=new GalleryAdapter(this,images);
        manager=new GridLayoutManager(this,3);
        totalimages=findViewById(R.id.gallery_total_images);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        onStart();
        {
            if(checkPermissions())
            {
                loadImages();
            }
            else {
                requestPermission();
            }
        }

    }

    private boolean checkPermissions() {
        int result= ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED)
        {
           return true;
        }
        else {
         return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0)
        {
            boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(accepted)
            {
                loadImages();
            }
            else {
                Toast.makeText(this, "You have denied the permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadImages()
    {
        boolean SDCard= Environment.getExternalStorageState().equals(MEDIA_MOUNTED);
        if(SDCard)
        {
            final String[] columns ={MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String order=MediaStore.Images.Media.DATE_TAKEN +" DESC";

            Cursor cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null,order);
            int count=cursor.getCount();
            totalimages.setText("Total Items: "+ count);

            for(int i=0;i<count; i++)
            {
                cursor.moveToPosition(i);
                int columnindex= cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                images.add(cursor.getString(columnindex));
            }

            recycler.getAdapter().notifyDataSetChanged();
            cursor.close();
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, READ_MEDIA_IMAGES)) {
            Toast.makeText(this, "Storage permission required", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_MEDIA_IMAGES}, 111);


    }



}