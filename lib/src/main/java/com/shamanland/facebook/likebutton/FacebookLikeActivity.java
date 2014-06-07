package com.shamanland.facebook.likebutton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;

import static com.shamanland.facebook.likebutton.BuildConfig.LOGGING;

public class FacebookLikeActivity extends Activity {
    private static final String LOG_TAG = FacebookLikeActivity.class.getSimpleName();

    public static final String PAGE_URL = "page.url";
    public static final String PAGE_TITLE = "page.title";
    public static final String PAGE_TEXT = "page.text";
    public static final String PAGE_PICTURE = "page.picture";
    public static final String APP_ID = "app.id";
    public static final String CONTENT_VIEW_ID = "content.view.id";
    public static final String OPTIONS = "options";

    private static final View.OnTouchListener SKIP_TOUCH = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    private ViewGroup mContainer;
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
                if (transport == null) {
                    return false;
                }

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

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                View progress = getProgressView(view);
                if (progress != null) {
                    progress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                View progress = getProgressView(view);
                if (progress != null) {
                    progress.setVisibility(View.GONE);
                }
            }

            private View getProgressView(WebView view) {
                final View progress;

                Object tag = view.getTag();
                if (tag instanceof View) {
                    progress = (View) tag;
                } else {
                    progress = createProgressBar(view);
                    view.setTag(progress);
                }

                return progress;
            }
        };
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(createRoot());
    }

    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);

        String content = generateContent();
        if (content == null) {
            Toast.makeText(getApplication(), R.string.com_facebook_like_activity_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (LOGGING) {
            Log.v(LOG_TAG, "onPostCreate: loadDataWithBaseURL: " + getIntent().getStringExtra(PAGE_URL));
        }

        createWindow().loadDataWithBaseURL(getIntent().getStringExtra(PAGE_URL), content, "text/html", "utf-8", null);
    }

    @Override
    public void onBackPressed() {
        final boolean handled;

        WebView window = mWindows.peekLast();
        if (window.canGoBack()) {
            window.goBack();
            handled = true;
        } else {
            handled = removeWindow(window);
        }

        if (handled) {
            mContainer.playSoundEffect(SoundEffectConstants.CLICK);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Iterator<WebView> it = mWindows.iterator();
        while (it.hasNext()) {
            WebView window = it.next();
            it.remove();

            detachFromParent(window);
            window.destroy();
        }
    }

    private String generateContent() {
        String url = getIntent().getStringExtra(PAGE_URL);
        if (url == null) {
            return null;
        }

        try {
            new java.net.URL(url);
        } catch (MalformedURLException ex) {
            if (LOGGING) {
                Log.wtf(LOG_TAG, ex);
            }

            return null;
        }

        String title = getIntent().getStringExtra(PAGE_TITLE);
        String text = getIntent().getStringExtra(PAGE_TEXT);
        Bitmap picture = getIntent().getParcelableExtra(PAGE_PICTURE);
        String appId = getIntent().getStringExtra(APP_ID);
        FacebookLikeOptions options = getIntent().getParcelableExtra(OPTIONS);
        if (options == null) {
            options = new FacebookLikeOptions();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<body>");

        if (title != null) {
            sb.append(options.titleOpen);
            sb.append(escapeHtml(title));
            sb.append(options.titleClose);
        }

        if (picture != null) {
            String picture64 = bitmapToBase64(picture, getPictureSize());
            if (picture64 != null) {
                sb.append("<img ");
                sb.append(options.pictureAttrs);
                sb.append("src='data:image/png;base64,").append(picture64).append("'");
                sb.append("/>");
            }
        }

        if (text != null) {
            sb.append(options.textOpen);
            sb.append(escapeHtml(text));
            sb.append(options.textClose);
        }

        appendUrl(url, appId, options, sb);

        sb.append("</body>");
        sb.append("</html>");

        try {
            return sb.toString();
        } catch (OutOfMemoryError ex) {
            if (LOGGING) {
                Log.wtf(LOG_TAG, ex);
            }

            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private void appendUrl(String url, String appId, FacebookLikeOptions options, StringBuilder sb) {
        sb.append("<iframe ");
        sb.append("style='");
        sb.append("display:block;clear:both;border:none;overflow:hidden;");
        sb.append("width:100%;");
        sb.append("'");

        sb.append("src='//www.facebook.com/plugins/like.php");
        sb.append("?href=").append(URLEncoder.encode(url));
        sb.append("&layout=").append(options.getLayoutString());
        sb.append("&action=").append(options.getActionString());
        sb.append("&show_faces=").append(options.showFaces);
        sb.append("&share=").append(options.share);

        if (appId != null && appId.trim().length() > 0) {
            sb.append("&appId=").append(appId);
        }

        sb.append("'");

        sb.append("scrolling='no'");
        sb.append("frameborder='0'");
        sb.append("allowTransparency='true'");
        sb.append("></iframe>");
    }

    private View createRoot() {
        View result = null;

        int contentViewId = getIntent().getIntExtra(CONTENT_VIEW_ID, 0);
        if (contentViewId != 0) {
            result = getLayoutInflater().inflate(contentViewId, null);
        }

        if (result != null) {
            View container = result.findViewById(R.id.com_facebook_like_container);
            if (container instanceof ViewGroup) {
                mContainer = (ViewGroup) container;
            }
        }

        if (mContainer == null) {
            mContainer = new FrameLayout(getContext());
            result = mContainer;
        }

        return result;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    protected WebView createWindow() {
        FrameLayout wrapper = new FrameLayout(getContext());
        WebView window = new WebView(getContext());
        window.getSettings().setJavaScriptEnabled(true);
        window.getSettings().setSupportMultipleWindows(true);
        window.getSettings().setSavePassword(false);
        window.setWebChromeClient(mWebChromeClient);
        window.setWebViewClient(mWebViewClient);

        wrapper.addView(window);
        mWindows.add(window);
        mContainer.addView(wrapper);
        return window;
    }

    protected View createProgressBar(WebView window) {
        ViewParent parent = window.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup wrapper = (ViewGroup) parent;

            View result = getLayoutInflater().inflate(R.layout.com_facebook_like_activity_progress, wrapper, false);
            if (result != null) {
                wrapper.addView(result, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                result.setOnTouchListener(SKIP_TOUCH);
                return result;
            }
        }

        return null;
    }

    protected boolean removeWindow(WebView window) {
        if (mWindows.size() > 1) {
            mWindows.remove(window);

            ViewParent parent = window.getParent();
            if (parent instanceof View) {
                mContainer.removeView((View) parent);
            }

            detachFromParent(window);
            window.destroy();
            return true;
        } else {
            window.stopLoading();
            window.loadDataWithBaseURL("about:blank", "<html></html>", "text/html", "utf-8", null);
            return false;
        }
    }

    public static String escapeHtml(String unsecure) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return Html.fromHtml(unsecure).toString();
        } else {
            return Html.escapeHtml(unsecure);
        }
    }

    public static String bitmapToBase64(Bitmap picture, int size) {
        try {
            int w = picture.getWidth();
            int h = picture.getHeight();

            int max = Math.max(w, h);
            if (max > size) {
                float factor = max / (float) size;
                w = (int) (w / factor);
                h = (int) (h / factor);

                picture = Bitmap.createScaledBitmap(picture, w, h, false);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Throwable ex) {
            if (LOGGING) {
                Log.wtf(LOG_TAG, ex);
            }

            return null;
        }
    }

    public static void detachFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public int getPictureSize() {
        return Math.min(
                getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight()
        ) / 4;
    }
}
