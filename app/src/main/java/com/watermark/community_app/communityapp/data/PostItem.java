package com.watermark.community_app.communityapp.data;

import android.graphics.drawable.BitmapDrawable;

import java.io.Serializable;

/**
 * Created by Blake on 3/2/2019.
 */
public class PostItem implements Serializable, ICommunityItem {
    private String title = "";
    private String mediaUrl = "";
    private String content = "";
    private BitmapDrawable postImage;

    public PostItem(String title_, String mediaUrl_, String content_, BitmapDrawable postImage_) {
        title = title_;
        mediaUrl = mediaUrl_;
        content = content_;
        postImage = postImage_;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getContent() {
        return content;
    }

    public BitmapDrawable getPostImage() {
        return postImage;
    }

    public void setTitle(String title_) {
        title = title_;
    }

    public void setMediaUrl(String mediaUrl_) {
        mediaUrl = mediaUrl_;
    }

    public void setContent(String content_) {
        content = content_;
    }

    public void setPostImage(BitmapDrawable postImage_) {
        postImage = postImage_;
    }
}
