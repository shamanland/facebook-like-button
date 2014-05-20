package com.shamanland.facebook.likebutton;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.Base64;
import android.view.SoundEffectConstants;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;

public class FacebookLikeActivity extends Activity {
    public static final String APP_ID = "app.id";
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String PICTURE = "picture";

    private FrameLayout mFrame;
    private final LinkedList<WebView> mWindows;
    private final WebChromeClient mWebChromeClient;
    private final WebViewClient mWebViewClient;

    public Context getContext() {
        return this;
    }

    public FacebookLikeActivity() {
        mWindows = new LinkedList<WebView>();

        mWebChromeClient = new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(createWindow());
                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public void onCloseWindow(WebView window) {
                removeWindow(window);
            }
        };

        mWebViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        mFrame = new FrameLayout(getContext());
        setContentView(mFrame);

        String appId = getIntent().getStringExtra(APP_ID);
        String url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);
        String text = getIntent().getStringExtra(TEXT);
        Bitmap picture = getIntent().getParcelableExtra(PICTURE);

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<body>");

        if (title != null) {
            sb.append("<h1>");
            sb.append(escapeHtml(title, false));
            sb.append("</h1>");
        }

        if (picture != null) {
            sb.append("<img align=\"left\" style=\"margin:4px;\"");
            sb.append("src=\"data:image/png;base64,");
            sb.append(bitmapToBase64(picture));
            sb.append("\"/>");
        }

        if (text != null) {
            sb.append("<p>");
            sb.append(escapeHtml(text, false));
            sb.append("</p>");
        }

        if (url != null) {
            sb.append("<iframe src=\"//www.facebook.com/plugins/like.php?href=");
            sb.append(URLEncoder.encode(url));
            sb.append("&amp;width&amp;layout=standard&amp;action=like&amp;show_faces=true&amp;share=true&amp;height=80&amp;");

            if (appId != null) {
                sb.append("appId=");
                sb.append(appId);
            }

            sb.append("\" scrolling=\"no\" frameborder=\"0\" style=\"border:none; overflow:hidden; height:80px;\" allowTransparency=\"true\"></iframe>");
        }

        sb.append("</body>");
        sb.append("</html>");

        createWindow().loadDataWithBaseURL(url, sb.toString(), "text/html", "utf-8", null);
    }

    protected WebView createWindow() {
        WebView window = new WebView(getContext());
        window.getSettings().setJavaScriptEnabled(true);
        window.getSettings().setSupportMultipleWindows(true);
        window.setWebChromeClient(mWebChromeClient);
        window.setWebViewClient(mWebViewClient);
        mWindows.add(window);
        mFrame.addView(window);
        return window;
    }

    protected void removeWindow(WebView window) {
        if (mWindows.size() > 1) {
            mWindows.remove(window);
            mFrame.removeView(window);
        } else {
            window.stopLoading();
            window.loadDataWithBaseURL("about:blank", "<html></html>", "text/html", "utf-8", null);
        }
    }

    @Override
    public void onBackPressed() {
        mFrame.playSoundEffect(SoundEffectConstants.CLICK);

        WebView window = mWindows.getLast();
        if (window.canGoBack()) {
            window.goBack();
        } else if (mWindows.size() > 1) {
            removeWindow(window);
        } else {
            super.onBackPressed();
        }
    }

    public static String escapeHtml(String unsecure, boolean noSpaces) {
        final String result;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            result = Html.fromHtml(unsecure).toString();
        } else {
            result = Html.escapeHtml(unsecure);
        }

        if (noSpaces) {
            return result.replaceAll("\\s+", "%20");
        }

        return result;
    }

    public static String bitmapToBase64(Bitmap picture) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
