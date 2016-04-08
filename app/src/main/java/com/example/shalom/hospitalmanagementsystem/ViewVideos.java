package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewVideos extends AppCompatActivity {
VideoView v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_videos);
        Intent i=getIntent();

        v1=(VideoView)findViewById(R.id.v1);
        String path1=i.getStringExtra("link");

        Uri uri= Uri.parse(path1);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(v1);
        mc.setMediaPlayer(v1);
        v1.setMediaController(mc);
        v1.setVideoURI(uri);
        v1.start();

    }
}
