package com.shamanland.facebook;

import com.facebook.Session;

public interface FacebookSessionOwner {
    Session getSession();

    void openForRead(Session session, Session.StatusCallback callback, String... permissions);

    void openForPublish(Session session, Session.StatusCallback callback, String... permissions);

    void requestNewReadPermissions(Session session, Session.StatusCallback callback, String... permissions);

    void requestNewPublishPermissions(Session session, Session.StatusCallback callback, String... permissions);
}
