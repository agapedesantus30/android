package edu.ggc.lutz.videoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final VideoView videoView = findViewById(R.id.vvAnim);
        Uri uri = Uri.parse("android.resource://"+ getPackageName() +"/raw/trainwreck");
        final MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoView);
        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.requestFocus();
                videoView.start();
            }
        });
    }
}
