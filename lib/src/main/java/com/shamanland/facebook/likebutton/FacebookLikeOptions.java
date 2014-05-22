package com.shamanland.facebook.likebutton;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public final class FacebookLikeOptions implements Parcelable {
    public enum Layout {
        STANDARD, BOX_COUNT, BUTTON_COUNT, BUTTON;

    }

    public enum Action {
        LIKE, RECOMMEND
    }

    public String titleOpen = "<h2>";
    public String titleClose = "</h2>";
    public String textOpen = "<p>";
    public String textClose = "</p>";
    public String pictureAttrs = "style='float:left;margin:4px;'";
    public Layout layout = Layout.STANDARD;
    public Action action = Action.LIKE;
    public boolean showFaces = true;
    public boolean share = true;

    public String getLayoutString() {
        return (layout != null ? layout : Layout.STANDARD).name().toLowerCase(Locale.US);
    }

    public String getActionString() {
        return (action != null ? action : Action.LIKE).name().toLowerCase(Locale.US);
    }

    public FacebookLikeOptions() {
        super();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(titleOpen);
        out.writeString(titleClose);
        out.writeString(textOpen);
        out.writeString(textClose);
        out.writeString(pictureAttrs);
        out.writeInt(layout != null ? layout.ordinal() : 0);
        out.writeInt(action != null ? action.ordinal() : 0);
        out.writeInt(showFaces ? 1 : 0);
        out.writeInt(share ? 1 : 0);
    }

    public static final Parcelable.Creator<FacebookLikeOptions> CREATOR = new Parcelable.Creator<FacebookLikeOptions>() {
        public FacebookLikeOptions createFromParcel(Parcel in) {
            return new FacebookLikeOptions(in);
        }

        public FacebookLikeOptions[] newArray(int size) {
            return new FacebookLikeOptions[size];
        }
    };

    private FacebookLikeOptions(Parcel in) {
        titleOpen = in.readString();
        titleClose = in.readString();
        textOpen = in.readString();
        textClose = in.readString();
        pictureAttrs = in.readString();
        layout = Layout.values()[in.readInt()];
        action = Action.values()[in.readInt()];
        showFaces = in.readInt() == 1;
        share = in.readInt() == 1;
    }
}
