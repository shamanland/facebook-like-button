package com.shamanland.facebook;

import android.app.Activity;

import com.facebook.Session;

public class ActivityFacebookSessionOwner extends DefaultFacebookSessionOwner {
    private final Activity mActivity;

    public ActivityFacebookSessionOwner(Activity activity, int requestCode) {
        super(requestCode);
        mActivity = activity;
    }

    @Override
    protected Session.OpenRequest createOpenRequest() {
        return new Session.OpenRequest(mActivity);
    }

    @Override
    protected Session.NewPermissionsRequest createNewPermissionsRequest() {
        return new Session.NewPermissionsRequest(mActivity);
    }
}
