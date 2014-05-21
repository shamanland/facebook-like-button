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
            sb.append(escapeHtml(title));
            sb.append("</h1>");
        }

        if (picture != null) {
            String picture64 = bitmapToBase64(picture);
            if (picture64 != null) {
                sb.append("<img ");
                sb.append("style='float:left;margin:4px;'");
                sb.append("src='data:image/png;base64,").append(picture64).append("'");
                sb.append("/>");
            }
        }

        if (text != null) {
            sb.append("<p>");
            sb.append(escapeHtml(text));
            sb.append("</p>");
        }

        if (url != null) {
            sb.append("<iframe ");
            sb.append("style='display:block;clear:both;border:none;overflow:hidden;'");

            sb.append("src='//www.facebook.com/plugins/like.php");
            sb.append("?href=").append(URLEncoder.encode(url));
            sb.append("&layout=standard");
            sb.append("&action=like");
            sb.append("&show_faces=true");
            sb.append("&share=true");

            if (appId != null) {
                sb.append("&appId=").append(appId);
            }

            sb.append("'");

            sb.append("scrolling='no'");
            sb.append("frameborder='0'");
            sb.append("allowTransparency='true'");
            sb.append("></iframe>");
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

    protected boolean removeWindow(WebView window) {
        if (mWindows.size() > 1) {
            mWindows.remove(window);
            mFrame.removeView(window);
            return true;
        } else {
            window.stopLoading();
            window.loadDataWithBaseURL("about:blank", "<html></html>", "text/html", "utf-8", null);
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        boolean handled = false;

        WebView window = mWindows.peekLast();
        if (window != null) {
            if (window.canGoBack()) {
                window.goBack();
                handled = true;
            } else {
                handled = removeWindow(window);
            }
        }

        if (handled) {
            mFrame.playSoundEffect(SoundEffectConstants.CLICK);
        } else {
            super.onBackPressed();
        }
    }

    public static String escapeHtml(String unsecure) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return Html.fromHtml(unsecure).toString();
        } else {
            return Html.escapeHtml(unsecure);
        }
    }

    public static String bitmapToBase64(Bitmap picture) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Throwable ex) {
            return null;
        }
    }
}
