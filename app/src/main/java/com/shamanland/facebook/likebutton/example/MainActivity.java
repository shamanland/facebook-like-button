package com.shamanland.facebook.likebutton.example;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.shamanland.facebook.likebutton.FacebookLikeActivity;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.activity_main);

        findViewById(R.id.com_facebook_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FacebookLikeActivity.class);
                intent.putExtra(FacebookLikeActivity.APP_ID, "690014507725915");
                intent.putExtra(FacebookLikeActivity.URL, "http://google.com");
                intent.putExtra(FacebookLikeActivity.TITLE, "Google Search");
                intent.putExtra(FacebookLikeActivity.TEXT, "This is the best search engine.");
                intent.putExtra(FacebookLikeActivity.PICTURE, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                startActivity(intent);
            }
        });
    }
}
