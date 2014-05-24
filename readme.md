Facebook Like Button
====

This is **custom implementation** of [Facebook 'Like' social plugin][8] for Android.

There is **no official implementation** in [Facebook Android SDK][12].

This library uses ``WebView`` to display ``<iframe>`` based plugin.

Gradle dependency
----

```
repositories {
    maven {
        url 'http://repo.shamanland.com'
    }
}

dependencies {
    compile 'com.shamanland:facebook-like-button:0.1.5'
}
```

Examples
----

Various layout available for any of your purposes:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBUUFTeXJBS3N2SGc" width="169" height="286" />

Button supports pressed styles according to Facebook color palette:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBbDBKSnNUUnowdUE" width="169" height="286" />

Custom web-page will be shown with ``<iframe>`` plugin:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBVHhhZXNaMVVQTnM" width="169" height="286" />

User will be asked to login on official Facebook site:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBbGtJejcyYTVvaVk" width="169" height="286" />

Original Facebook ``<iframe>`` based plugin works inside ``WebView``:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBNnU0YzlDVWxKems" width="169" height="286" />

You can set up any of Facebook options:

<img src="https://drive.google.com/uc?id=0Bwh0SNLPmjQBQ1lrU3JObXIwWUk" width="169" height="286" />

How to use
----

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

Properties
----

**FacebookLikeButton**

``app:pageUrl`` - string - **required** - target url

``app:pageTitle`` - string - *recommended* - title of page

``app:pageText`` - string - *recommended* - short description of page

``app:pagePicture`` - bitmap - *recommended* - small picture from page

``app:appId`` - string - *optional* - id of registered Facebook application

``app:optLayout`` - enum - *optional* - type of layout inside <iframe>

values: ``standard``, ``box_count``, ``button_count``, ``button``

``app:optAction`` - enum - *optional* - type of action inside <iframe>

values: ``like``, ``recommend``

``app:optShowFaces`` - boolean - *optional* - enable/disable faces of friends inside <iframe>

``app:optShare`` - boolean - *optional* - enable/disable 'Share' button inside <iframe>

``app:contentViewId`` - layout - *not-recommended* - custom layout for dialog, must contains ViewGroup with id ``R.id.com_facebook_like_container``

``app:optTitleOpen`` - string - *not-recommended* - open-tag for title

``app:optTitleClose`` - string - *not-recommended* - close-tag for title

``app:optTextOpen`` - string - *not-recommended* - open-tag for text

``app:optTextClose`` - string - *not-recommended* - close-tag for text

``app:optPictureAttrs`` - string - *not-recommended* - attributes for <img> tag


**FacebookLikeBox**

``calloutMarker`` - enum - *recommended* - position of callout triangle

values: ``none``, ``left``, ``top``, ``right``, ``bottom``, ``all``

``boxFillColor`` - color - *optional* - background color of box

``boxStrokeColor`` - color - *optional* - stroke color of box

``boxStrokeWidth`` - dimension - *optional* - stroke width of box

``boxCornersRadius`` - dimension - *optional* - radius of corners of box


**FacebookLikePlugin**

``likeId`` - id - *recommended* - id of child ``FacebookLikeButton``, default value if ``R.id.com_facebook_like``

``boxId`` - id - *recommended* - id of child ``FacebookLikeBox``, default value if ``R.id.com_facebook_box``

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
