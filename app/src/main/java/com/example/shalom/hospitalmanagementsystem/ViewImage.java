package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import jsonDtos.StringDto;

public class ViewImage extends AppCompatActivity {
    static StringDto in;
    Intent intent;
    String TAG = "Debugg";
    Button enter;
    EditText ed1;
    ImageView ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ed2 = (ImageView) findViewById(R.id.ed2);

        intent = getIntent();
//  String pos=intent.getStringExtra("pos");
        int po = intent.getIntExtra("pos", 1);
        Log.d("debugdfsfdsfdsfsf", "" + po);
        ArrayList<String> arrayList = new StaticObjects().getHt();

        byte[] decodedString = Base64.decode(arrayList.get(po).getBytes());
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);

        ed2.setImageBitmap(bitmap);


    }

}