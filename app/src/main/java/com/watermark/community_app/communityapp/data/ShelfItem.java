package com.watermark.community_app.communityapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Blake on 3/15/2019.
 */
public class ShelfItem implements Serializable, ICommunityItem {
    String title = "";
    ArrayList<PostItem> posts;
    ArrayList<ShelfItem> nestedShelves;

    public ShelfItem(String title_, ArrayList<PostItem> posts_, ArrayList<ShelfItem> nestedShelves_) {
        title = title_;
        posts = posts_;
        nestedShelves = nestedShelves_;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public ArrayList<PostItem> getPosts() {
        return posts;
    }

    public ArrayList<ShelfItem> getShelves() {
        return nestedShelves;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + "\n");
        for (PostItem p : posts) {
            sb.append(p.getTitle() + " ");
        }
        sb.append("\n");
        for (ShelfItem i : nestedShelves) {
            sb.append(i.toString());
        }

        return sb.toString();
    }
}
