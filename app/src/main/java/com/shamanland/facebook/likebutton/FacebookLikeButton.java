package com.shamanland.facebook.likebutton;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.shamanland.facebook.FacebookSessionOwner;

public class FacebookLikeButton extends Button {
    private static final String LOG_TAG = FacebookLikeButton.class.getSimpleName();

    private FacebookSessionOwner mOwner;
    private Session.StatusCallback mStatusCallback;
    private boolean mAttachedToWindow;

    private String mUrl;

    public void setOwner(FacebookSessionOwner owner) {
        mOwner = owner;
    }

    public void setUrl(String value) {
        boolean changed = mUrl == null && value != null || mUrl != null && !mUrl.equals(value);

        mUrl = value;

        if (changed) {
            onUrlChanged();
        }
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
            Log.wtf(LOG_TAG, exception);
        }
    }

    protected void onUrlChanged() {
        if (mUrl != null) {
            new FacebookGetLikesCountTask(new FacebookGetLikesCountTaskListener() {
                @Override
                public void onPostExecute(int result) {
                    if (mAttachedToWindow) {
                        onGetLikesCountCompleted(result);
                    }
                }
            }).execute(mUrl);
        }
    }

    protected void onGetLikesCountCompleted(int result) {
        if (result < 0) {
            Toast.makeText(getContext(), "failed get count: " + result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "get count: " + result, Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean onClick() {
        boolean result = false;

        FacebookSessionOwner owner = mOwner;
        if (owner != null) {
            Session session = owner.getSession();
            if (session != null) {
                if (!session.isOpened() && !session.isClosed()) {
                    owner.openForPublish(session, mStatusCallback, "publish_actions");
                } else {
                    if (session.isPermissionGranted("publish_actions")) {
                        performLikeRequest(session);
                        result = true;
                    } else {
                        owner.requestNewPublishPermissions(session, mStatusCallback, "publish_actions");
                    }
                }
            }
        }

        return result;
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
