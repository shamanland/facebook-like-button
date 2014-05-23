package com.shamanland.facebook.likebutton;

import android.os.AsyncTask;

import com.shamanland.facebook.likebutton.FacebookLinkStatProcessor.Result;

import java.lang.ref.WeakReference;

public class FacebookLinkStatTask extends AsyncTask<String, Void, Result> {
    public interface Listener {
        void onPostExecute(Result result);
    }

    private final FacebookLinkStatProcessor mProcessor;
    private final WeakReference<Listener> mListener;

    public FacebookLinkStatTask(FacebookLinkStatProcessor processor, Listener listener) {
        mProcessor = processor;
        mListener = new WeakReference<Listener>(listener);
    }

    @Override
    protected Result doInBackground(String... params) {
        try {
            return mProcessor.processUrl(params[0]);
        } catch (Throwable ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        Listener listener = mListener.get();
        if (listener != null) {
            listener.onPostExecute(result);
        }
    }
}
