package com.shamanland.facebook;

import android.support.v4.app.Fragment;

import com.facebook.Session;

public class FragmentFacebookSessionOwner extends DefaultFacebookSessionOwner {
    private final Fragment mFragment;

    public FragmentFacebookSessionOwner(Fragment fragment, int requestCode) {
        super(requestCode);
        mFragment = fragment;
    }

    @Override
    protected Session.OpenRequest createOpenRequest() {
        return new Session.OpenRequest(mFragment);
    }

    @Override
    protected Session.NewPermissionsRequest createNewPermissionsRequest() {
        return new Session.NewPermissionsRequest(mFragment);
    }
}
