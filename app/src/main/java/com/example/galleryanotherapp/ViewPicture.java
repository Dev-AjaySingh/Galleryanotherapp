package com.example.galleryanotherapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;

public class ViewPicture extends AppCompatActivity {
    ZoomageView image;
    String image_file;
    ImageButton bacbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);

        bacbutton = findViewById(R.id.backbutton);



        image_file = getIntent().getStringExtra("image_file");

        if (image_file == null) {
            Toast.makeText(this, "Invalid image file", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        File file = new File(image_file);
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        image = findViewById(R.id.image);

        Glide.with(this)
                .load(Uri.fromFile(file))
                .into(image);

        bacbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}