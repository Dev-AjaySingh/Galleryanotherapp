package com.example.galleryanotherapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;

public class viewPicture extends AppCompatActivity {

    ZoomageView image;
    String image_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);
        image_file=getIntent().getStringExtra("image_file");
        File file=new File(image_file);
        image=findViewById(R.id.image);
        if(file.exists()) {
            Glide.with(this).load(image_file).into(image);
        }
    }
}