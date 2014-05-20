package com.shamanland.facebook;

import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;

public abstract class DefaultFacebookSessionOwner implements FacebookSessionOwner {
    private final int mRequestCode;

    protected DefaultFacebookSessionOwner(int requestCode) {
        mRequestCode = requestCode;
    }

    protected abstract Session.OpenRequest createOpenRequest();

    protected abstract Session.NewPermissionsRequest createNewPermissionsRequest();

    protected SessionLoginBehavior getLoginBehavior() {
        return SessionLoginBehavior.SSO_WITH_FALLBACK;
    }

    protected SessionDefaultAudience getDefaultAudience() {
        return SessionDefaultAudience.EVERYONE;
    }

    protected int getRequestCode() {
        return mRequestCode;
    }

    @Override
    public Session getSession() {
        return Session.getActiveSession();
    }

    @Override
    public void openForRead(Session session, Session.StatusCallback callback, String... permissions) {
        session.openForRead(createOpenRequest()
                        .setRequestCode(getRequestCode())
                        .setCallback(callback)
                        .setPermissions(permissions)
                        .setLoginBehavior(getLoginBehavior())
                        .setDefaultAudience(getDefaultAudience())
        );
    }

    @Override
    public void openForPublish(Session session, Session.StatusCallback callback, String... permissions) {
        session.openForPublish(createOpenRequest()
                        .setRequestCode(getRequestCode())
                        .setCallback(callback)
                        .setPermissions(permissions)
                        .setLoginBehavior(getLoginBehavior())
                        .setDefaultAudience(getDefaultAudience())
        );
    }

    @Override
    public void requestNewReadPermissions(Session session, Session.StatusCallback callback, String... permissions) {
        session.requestNewReadPermissions(createNewPermissionsRequest()
                        .setRequestCode(getRequestCode())
                        .setCallback(callback)
                        .setLoginBehavior(getLoginBehavior())
                        .setDefaultAudience(getDefaultAudience())
        );
    }

    @Override
    public void requestNewPublishPermissions(Session session, Session.StatusCallback callback, String... permissions) {
        session.requestNewPublishPermissions(createNewPermissionsRequest()
                        .setRequestCode(getRequestCode())
                        .setCallback(callback)
                        .setLoginBehavior(getLoginBehavior())
                        .setDefaultAudience(getDefaultAudience())
        );
    }
}
