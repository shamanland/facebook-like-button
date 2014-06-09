package com.shamanland.facebook.likebutton.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shamanland.facebook.likebutton.FacebookLikeButton;

public class LikeAdapter extends BaseAdapter {
    private static final String[] sTitles = {
            "Android FontIcon library available from Maven Central",
            "Android Dashboards - June, 4th",
            "Facebook SDK available from Maven repository",
            "Android Dashboards - May, 1st",
            "FontIcon Library now supports ColorStateList",
            "Android Dashboards - March, 3",
            "Android Dashboards. February 4th. Gingerbread still alive.",
            "Android. Support library. Nested fragments and startActivityForResult()",
            "Android Dashboards - Statistics of first 7 days of 2014 year",
            "How to redirect raw stream to logcat?",
            "Font-icon technique in Android. Critical issue!",
            "Android Dashboards - December, 2",
            "Naming convention for Android layout resources",
            "How to use icon-fonts in Android",
            "Android Dashboards - November, 1",
            "Hello world",
    };

    private static final String[] sUrls = {
            "http://blog.shamanland.com/2014/06/android-fonticon-library-available-from.html",
            "http://blog.shamanland.com/2014/06/android-dashboards-june-4th.html",
            "http://blog.shamanland.com/2014/05/add-facebook-sdk-to-project.html",
            "http://blog.shamanland.com/2014/05/android-dashboards-may-1st.html",
            "http://blog.shamanland.com/2014/04/fonticon-color-selector.html",
            "http://blog.shamanland.com/2014/03/android-dashboards-march-3.html",
            "http://blog.shamanland.com/2014/02/android-dashboards-february-4th.html",
            "http://blog.shamanland.com/2014/01/nested-fragments-for-result.html",
            "http://blog.shamanland.com/2014/01/android-dashboards-january-2014.html",
            "http://blog.shamanland.com/2013/12/redirect-stream-to-logcat.html",
            "http://blog.shamanland.com/2013/12/font-icon-technic-in-android-critical.html",
            "http://blog.shamanland.com/2013/12/android-dashboards-december-2.html",
            "http://blog.shamanland.com/2013/11/naming-convention-for-android-layout.html",
            "http://blog.shamanland.com/2013/11/how-to-use-icon-fonts-in-android.html",
            "http://blog.shamanland.com/2013/11/android-dashboards.html",
            "http://blog.shamanland.com/2013/10/test.html",
    };

    private static final String[] sContents = {
            "The latest version of FontIcon library for Android is available from Maven Central repository. It's easy to include it in project: Gradle repositories { mavenCentral() } dependencies { compile 'com.shamanland:fonticon:0.1.6' } Links Sources Project page",
            "This month Honeycomb devices are disappeared from official statistics about Android devices. There are three stable groups of API versions: Gingerbread (API 10) - 15% Ice Cream Sandwich and Jelly Bean (API 15-18) - 70% KitKat (API 19) - 14% Check details in this public spreadsheet.",
            "I don't know why Facebook still not published theirs SDK to Maven Central. Well, nobody forbids us to deploy it to Sonatype repository!",
            "Latest statistics about Android devices is available on developer.android.com. As usual we publish additional statistics in public Google document. You can check previous history and forecast for next month.",
            "New feature added to the FontIcon Library latest version 0.1.1. Now it's possible to set color-selector for icons. FontIconDrawable will handle changing the state, propagated from parental Button, TextView, etc.",
            "Latest statistics about Android devices is available on developer.android.com. As usual we publish additional statistics in public Google document. You can check previous history and forecast for next month.",
            "Google published new statistics about active Android versions. The most important information is about Gingerbread. Many developers dream to stop support it. But it's still alive! Last month we made forecast and it was wrong. Check updated document and make your own conclusion: support it or not.",
            "This post will be dedicated for nested Fragments from support library and for their big issue. There are available methods inside Fragment: startActivityForResult() and onActivityResult(). First one just delegates invocation to FragmentActivty.startActivityFromFragment(), and second one is called from FragmentActivity.onActivityResult(). If this behavior just wrapped around Activity, then how result is delivered into Fragment instance?",
            "Android developers still should care about Gingerbread users. Every month Google publishes platform statistics collected from Play Store. We are keeping previous statistics and it's quite easy to make some forecast. Month ago we made such forecast for January. Let's check it...",
            "Sometimes there is necessary to redirect lot of data, such as xml-file or something else, to logcat output. Especially useful to see HTTP traffic of your interaction with RESTful server (obviously in debug-mode only). Let's try to wrap and re-direct Java-stream to Android Logcat!",
            "This post oriented for those who already read and used font-icon drawables from previous post. During testing our application we found big issue - on some devices font-icons were invisible in ActionBar. This issue threatens this solution to be deleted from our project.",
            "Android lost Gingerbread users as usual by 2% per month. Detailed statistics and forecast for next month is available here. Data originally captured from developer.android.com.",
            "Every time when I was creating new layout resource there was issue: how to name it?",
            "There is popular web-technique for creating awesome icons - generate font with custom glyphs and use single unicode character with specified typeface. Look here for example and let’s try to use this technique in Android.",
            "Android Gingerbread lost again 2% of users. See detailed statistics and forecast to the next month in this public document. Data originally captured from developer.android.com.",
            "Today I started my blog!",
    };

    private static final String[] sTimes = {
            "June 07, 2014",
            "June 07, 2014",
            "May 19, 2014",
            "May 05, 2014",
            "April 23, 2014",
            "March 05, 2014",
            "February 07, 2014",
            "January 14, 2014",
            "January 12, 2014",
            "December 23, 2013",
            "December 19, 2013",
            "December 09, 2013",
            "November 25, 2013",
            "November 13, 2013",
            "November 03, 2013",
            "October 31, 2013",
    };

    @Override
    public int getCount() {
        return sTitles.length;
    }

    @Override
    public String getItem(int position) {
        return sUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        } else {
            result = convertView;
        }

        ViewHolder holder = ViewHolder.obtain(result);

        holder.title.setText(sTitles[position]);
        holder.time.setText(sTimes[position]);
        holder.content.setText(sContents[position]);

        return result;
    }

    static class ViewHolder {
        TextView title;
        TextView time;
        TextView content;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
        }

        static ViewHolder obtain(View view) {
            final ViewHolder result;

            Object tag = view.getTag();
            if (tag instanceof ViewHolder) {
                result = (ViewHolder) tag;
            } else {
                result = new ViewHolder(view);
                view.setTag(result);
            }

            return result;
        }
    }
}
