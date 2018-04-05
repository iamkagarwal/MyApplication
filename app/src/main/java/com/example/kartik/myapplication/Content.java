package com.example.kartik.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;

public class Content extends YouTubeBaseActivity {
    YouTubePlayerView video;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Button play, logout ;
           ImageButton feedback;
           FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.content);
        firebaseAuth = FirebaseAuth.getInstance();
        video = findViewById(R.id.ytVideo);
        play = findViewById(R.id.play);
        feedback = findViewById(R.id.feedbackButton);
        logout = findViewById(R.id.signOut);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo("xcJtL7QggTI");
                       }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.initialize(PlayerConfig.API_KEY,onInitializedListener);

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Content.this,Feedback.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
                finish();
                Intent i = new Intent(Content.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
