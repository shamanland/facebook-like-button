package com.shamanland.facebook.likebutton;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookGetLikesCountTask extends AsyncTask<String, Void, Integer> {
    private static final Pattern SHARES = Pattern.compile("\\s*\"shares\"\\s*:\\s*(\\d+)");

    private final FacebookGetLikesCountTaskListener mListener;

    public FacebookGetLikesCountTask(FacebookGetLikesCountTaskListener listener) {
        mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        if (params != null && params.length > 0) {
            try {
                return processUrl(params[0]);
            } catch (Throwable ex) {
                return -1;
            }
        }

        return -1;
    }

    private Integer processUrl(String url) throws IOException {
        URLConnection connection = new URL("https://graph.facebook.com/" + url).openConnection();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = SHARES.matcher(line);
                if (m.find()) {
                    String number = m.group(1);
                    return Integer.parseInt(number);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }

            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer count) {
        mListener.onPostExecute(count);
    }
}
