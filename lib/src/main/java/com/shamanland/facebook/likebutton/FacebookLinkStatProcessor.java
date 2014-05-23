package com.shamanland.facebook.likebutton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FacebookLinkStatProcessor {
    public static class Result {
        public String url;
        public long shares;
        public long comments;
    }

    protected String getRequestUrl(String url) {
        return "https://graph.facebook.com/" + url;
    }

    protected Result getResult(String url, JSONObject json) {
        Result result = new Result();
        result.url = url;
        result.shares = getShares(json);
        result.comments = getComments(json);
        return result;
    }

    protected long getShares(JSONObject json) {
        return json.optLong("shares", 0);
    }

    protected long getComments(JSONObject json) {
        return json.optLong("comments", 0);
    }

    public Result processUrl(String url) throws IOException, JSONException {
        URLConnection connection = new URL(getRequestUrl(url)).openConnection();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return getResult(url, new JSONObject(sb.toString()));
        } finally {
            if (reader != null) {
                reader.close();
            }

            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
        }
    }
}
