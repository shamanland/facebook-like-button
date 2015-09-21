Facebook Like Button
====

[![Build Status](https://travis-ci.org/shamanland/facebook-like-button.svg?branch=master)](https://travis-ci.org/shamanland/facebook-like-button)

Implementation of [Facebook 'Like' social plugin][8] for Android.

Official [Facebook SDK][12] does not provide such component for Android.

This library uses ``WebView`` to display ``<iframe>`` based plugin.

Project page
----

http://blog.shamanland.com/p/facebook-like-button.html

Gradle dependency
----

    dependencies {
        compile 'com.shamanland:facebook-like-button:0.1.8'
    }

Screenshots
----

Easy integration with ListView.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBOWRYYU9Bb1pwWk0"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBU0JpdUI2YjBqLTg" width="189" /></a>

Various layout available for any of your purposes.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBUUFTeXJBS3N2SGc"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBZUh6TThEdlJvZXM" width="189" /></a>

Custom web-page will be shown with &lt;iframe&gt; plugin.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBVHhhZXNaMVVQTnM"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBdHBUNE5uM0ZwZ2M" width="189" /></a>

User will be asked to login on official Facebook site.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBbGtJejcyYTVvaVk"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBTVZJYktuWUpSUHc" width="189" /></a>

Original Facebook &lt;iframe&gt; based plugin works inside WebView.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBNnU0YzlDVWxKems"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBeVhYR1dsYkw0cHM" width="189" /></a>

You can set up any of Facebook options.

<a href="https://drive.google.com/uc?id=0Bwh0SNLPmjQBQ1lrU3JObXIwWUk"><img border="0" height="320" src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBMy1xWTNyVnhYVEk" width="189" /></a>

How to use
----

1. Declare FacebookLikeActivity in your AndroidManifest.xml:
```
<activity
    android:name="com.shamanland.facebook.likebutton.FacebookLikeActivity"
    android:label="@string/facebook"
    android:theme="@style/Theme.Facebook.Like"
    />
```

2. Insert one of possible buttons in your layout:

Single 'Like' button: ![9]
```
<com.shamanland.facebook.likebutton.FacebookLikeButton
        style="@style/Widget.FacebookLikeButton"
        app:pageUrl="http://blog.shamanland.com/"
        app:pageTitle="Developer's notes"
        app:pageText="This is blog about Android development."
        app:pagePicture="@drawable/ic_launcher"
        />
```

'Like' plugin with box: ![10]
```
<!-- FacebookLikePlugin extends LinearLayout -->
<com.shamanland.facebook.likebutton.FacebookLikePlugin
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        >
    <com.shamanland.facebook.likebutton.FacebookLikeButton
            style="@style/Widget.FacebookLikeButton"
            app:pageUrl="http://blog.shamanland.com/"
            app:pageTitle="Developer's notes"
            app:pageText="This is blog about Android development."
            app:pagePicture="@drawable/ic_launcher"
            />
    <com.shamanland.facebook.likebutton.FacebookLikeBox
            style="@style/Widget.FacebookLikeBox"
            app:calloutMarker="left"
            />
</com.shamanland.facebook.likebutton.FacebookLikePlugin>
```

'Like' plugin with box above, custom name: ![11]
```
<!-- FacebookLikePlugin extends LinearLayout -->
<com.shamanland.facebook.likebutton.FacebookLikePlugin
        android:layout_width="80dp"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        >
    <com.shamanland.facebook.likebutton.FacebookLikeBox
            style="@style/Widget.FacebookLikeBox"
            android:layout_width="match_parent"
            app:calloutMarker="bottom"
            />
    <com.shamanland.facebook.likebutton.FacebookLikeButton
            style="@style/Widget.FacebookLikeButton"
            android:layout_width="match_parent"
            android:text="@string/share"
            app:pageUrl="http://blog.shamanland.com/"
            app:pageTitle="Developer's notes"
            app:pageText="This is blog about Android development."
            app:pagePicture="@drawable/ic_launcher"
            />
</com.shamanland.facebook.likebutton.FacebookLikePlugin>
```

License
--------

    Copyright 2014 ShamanLand.Com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBUUFTeXJBS3N2SGc
[2]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBbDBKSnNUUnowdUE
[3]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBVHhhZXNaMVVQTnM
[4]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBbGtJejcyYTVvaVk
[5]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBNnU0YzlDVWxKems
[6]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBQ1lrU3JObXIwWUk
[7]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBWGt4ZjFlcUhoak0
[8]: https://developers.facebook.com/docs/plugins/like-button
[9]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBRGRSdDhFUXJReUU
[10]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBa2lwanpVbm56T00
[11]: https://drive.google.com/uc?id=0Bwh0SNLPmjQBZ2xUTzlVbzd2LXM
[12]: http://blog.shamanland.com/2014/05/add-facebook-sdk-to-project.html
