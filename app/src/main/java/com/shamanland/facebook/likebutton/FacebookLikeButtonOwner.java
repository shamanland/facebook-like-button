package com.shamanland.facebook.likebutton;

import android.os.Bundle;

public interface FacebookLikeButtonOwner {
    String URL = "url";
    String TITLE = "title";
    String TEXT = "text";
    String PICTURE = "picture";

    Bundle getMetaData(String url);
}
