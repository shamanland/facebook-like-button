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

    public FacebookLikeOptions setTitleOpen(String titleOpen) {
        this.titleOpen = titleOpen;
        return this;
    }

    public FacebookLikeOptions setTitleClose(String titleClose) {
        this.titleClose = titleClose;
        return this;
    }

    public FacebookLikeOptions setTextOpen(String textOpen) {
        this.textOpen = textOpen;
        return this;
    }

    public FacebookLikeOptions setTextClose(String textClose) {
        this.textClose = textClose;
        return this;
    }

    public FacebookLikeOptions setPictureAttrs(String pictureAttrs) {
        this.pictureAttrs = pictureAttrs;
        return this;
    }

    public FacebookLikeOptions setLayout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public FacebookLikeOptions setAction(Action action) {
        this.action = action;
        return this;
    }

    public FacebookLikeOptions setShowFaces(boolean showFaces) {
        this.showFaces = showFaces;
        return this;
    }

    public FacebookLikeOptions setShare(boolean share) {
        this.share = share;
        return this;
    }

    public FacebookLikeOptions() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object) this).getClass() != o.getClass()) return false;

        FacebookLikeOptions that = (FacebookLikeOptions) o;

        if (share != that.share) return false;
        if (showFaces != that.showFaces) return false;
        if (action != that.action) return false;
        if (layout != that.layout) return false;
        if (pictureAttrs != null ? !pictureAttrs.equals(that.pictureAttrs) : that.pictureAttrs != null)
            return false;
        if (textClose != null ? !textClose.equals(that.textClose) : that.textClose != null)
            return false;
        if (textOpen != null ? !textOpen.equals(that.textOpen) : that.textOpen != null)
            return false;
        if (titleClose != null ? !titleClose.equals(that.titleClose) : that.titleClose != null)
            return false;
        if (titleOpen != null ? !titleOpen.equals(that.titleOpen) : that.titleOpen != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = titleOpen != null ? titleOpen.hashCode() : 0;
        result = 31 * result + (titleClose != null ? titleClose.hashCode() : 0);
        result = 31 * result + (textOpen != null ? textOpen.hashCode() : 0);
        result = 31 * result + (textClose != null ? textClose.hashCode() : 0);
        result = 31 * result + (pictureAttrs != null ? pictureAttrs.hashCode() : 0);
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (showFaces ? 1 : 0);
        result = 31 * result + (share ? 1 : 0);
        return result;
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
