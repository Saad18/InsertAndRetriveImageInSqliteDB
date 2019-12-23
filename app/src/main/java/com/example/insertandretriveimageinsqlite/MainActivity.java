package com.example.insertandretriveimageinsqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    Button loadBtn,insertBtn;
    ImageView showImg,loadImg;
    static final int REQUEST_IMAGE_PICKER = 1;
    DatabseHelper databseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,REQUEST_IMAGE_PICKER);
                }

            }
        });
    }

    public void init(){
        loadBtn = findViewById(R.id.loadBtn);
        insertBtn = findViewById(R.id.insertBtn);
        showImg = findViewById(R.id.showImg);
        loadImg = findViewById(R.id.loadImg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICKER)

                try {
                    assert data != null;
                    Uri uri = data.getData();
                    assert uri != null;
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    loadImg.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                }

            }

    public void insertImage(View view){
        loadImg.setDrawingCacheEnabled(true);
        loadImg.buildDrawingCache();
        Bitmap bitmap = loadImg.getDrawingCache();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,arrayOutputStream);
        byte[] data = arrayOutputStream.toByteArray();
        databseHelper = new DatabseHelper(this);
        databseHelper.insertToDb(data);
        Toast.makeText(this, "Image saved to DB successfully", Toast.LENGTH_SHORT).show();

    }

    public void displayImage(View view){
        Cursor cursor = databseHelper.showData();
        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            showImg.setImageBitmap(bitmap);
            Toast.makeText(this,"display successfull",Toast.LENGTH_SHORT).show();
        }

    }


}
