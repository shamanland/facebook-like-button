package com.shamanland.facebook.likebutton.example;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.shamanland.facebook.likebutton.FacebookLikeActivity;

public class MainActivity extends ActionBarActivity {
    private static final String MY_APP_ID = null;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.activity_main);

        findViewById(R.id.com_facebook_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FacebookLikeActivity.class);
                intent.putExtra(FacebookLikeActivity.URL, "http://blog.shamanland.com/");
                intent.putExtra(FacebookLikeActivity.TITLE, "Developer's notes");
                intent.putExtra(FacebookLikeActivity.TEXT, "This is blog about Android development.");
                intent.putExtra(FacebookLikeActivity.PICTURE, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                intent.putExtra(FacebookLikeActivity.APP_ID, MY_APP_ID);
                startActivity(intent);
            }
        });
    }
}
