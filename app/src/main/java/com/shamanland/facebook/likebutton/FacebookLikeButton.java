package com.shamanland.facebook.likebutton;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;

public class FacebookLikeButton extends Button {
    private Object mOwner;
    private int mRequestCode;

    private Session.StatusCallback mStatusCallback;
    private boolean mAttachedToWindow;

    private String mUrl;

    public void setOwner(Activity activity) {
        mOwner = activity;
    }

    public void setOwner(Fragment fragment) {
        mOwner = fragment;
    }

    public void setRequestCode(int value) {
        mRequestCode = value;
    }

    public void setUrl(String value) {
        mUrl = value;
    }

    public FacebookLikeButton(Context context) {
        super(context);
        postInit();
    }

    public FacebookLikeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInit();
    }

    public FacebookLikeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        postInit();
    }

    private void postInit() {
        mStatusCallback = new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChange(session, state, exception);
            }
        };

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttachedToWindow) {
                    FacebookLikeButton.this.onClick();
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttachedToWindow = false;
    }

    protected void onSessionStateChange(Session session, SessionState state, Exception exception) {
        Toast.makeText(getContext(), "state: " + state, Toast.LENGTH_SHORT).show();

        if (exception != null) {
            Log.wtf("FACEBOOK", exception);
        }
    }

    protected boolean onClick() {
        boolean result = false;

        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isOpened() && !session.isClosed()) {
                session.openForPublish(createOpenRequest()
                        .setRequestCode(mRequestCode)
                        .setPermissions("publish_actions")
                        .setCallback(mStatusCallback));
            } else {
                if (session.isPermissionGranted("publish_actions")) {
                    performLikeRequest(session);
                } else {
                    session.requestNewPublishPermissions(createNewPermissionsRequest("publish_actions")
                            .setCallback(mStatusCallback));
                }
            }
        }

        return result;
    }

    private Session.OpenRequest createOpenRequest() {
        if (mOwner instanceof Activity) {
            return new Session.OpenRequest((Activity) mOwner);
        } else if (mOwner instanceof Fragment) {
            return new Session.OpenRequest((Fragment) mOwner);
        } else {
            throw new IllegalStateException(String.valueOf(mOwner));
        }
    }

    private Session.NewPermissionsRequest createNewPermissionsRequest(String... permissions) {
        if (mOwner instanceof Activity) {
            return new Session.NewPermissionsRequest((Activity) mOwner, permissions);
        } else if (mOwner instanceof Fragment) {
            return new Session.NewPermissionsRequest((Fragment) mOwner, permissions);
        } else {
            throw new IllegalStateException(String.valueOf(mOwner));
        }
    }

    public void performLikeRequest(Session session) {
        Bundle params = new Bundle();
        params.putString("object", mUrl + "?time=" + SystemClock.uptimeMillis());

        new Request(
                session,
                "/me/og.likes",
                params,
                HttpMethod.POST,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        if (mAttachedToWindow) {
                            onLikeRequestCompleted(response);
                        }
                    }
                }
        ).executeAsync();
    }

    protected void onLikeRequestCompleted(Response response) {
        FacebookRequestError error = response.getError();
        if (error != null && error.getErrorCode() != 0) {
            onLikeRequestFailed(error);
        } else {
            onLikeRequestSuccess(response.getGraphObject());
        }
    }

    protected void onLikeRequestFailed(FacebookRequestError error) {
        Toast.makeText(getContext(), "failed: " + error, Toast.LENGTH_SHORT).show();
    }

    protected void onLikeRequestSuccess(GraphObject og) {
        Toast.makeText(getContext(), "success: " + og, Toast.LENGTH_SHORT).show();
    }
}
