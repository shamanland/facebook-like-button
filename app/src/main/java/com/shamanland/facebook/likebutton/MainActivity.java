package com.shamanland.facebook.likebutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int FACEBOOK_LOGIN_REQUEST_CODE = 1;

    private UiLifecycleHelper mUiHelper;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (state != null) {
                session = Session.restoreSession(this, null, null, state);
            }

            if (session == null) {
                session = new Session.Builder(this)
                        .setApplicationId("690014507725915")
                        .build();
            }

            Session.setActiveSession(session);
        }

        mUiHelper = new UiLifecycleHelper(this, null);
        mUiHelper.onCreate(state);

        setContentView(R.layout.activity_main);

        FacebookLikeButton like = (FacebookLikeButton) findViewById(R.id.facebook_like);
        like.setOwner(new FacebookLikeButtonOwner() {
            @Override
            public Bundle getMetaData(String url) {
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, "This is Google");
                return bundle;
            }
        });

        like.setUrl("http://google.com/");
    }

    @Override
    public void onResume() {
        super.onResume();
        mUiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUiHelper.onSaveInstanceState(outState);
    }
}
