package com.watermark.community_app.communityapp.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Blake on 3/15/2019.
 */
public class ShelfItem implements Serializable {
    String title = "";
    ArrayList<PostData> posts;
    ArrayList<ShelfItem> nestedShelves = new ArrayList<>();

    public ShelfItem(String title_, ArrayList<PostData> posts_, ArrayList<ShelfItem> nestedShelves_) {
        title = title_;
        posts = posts_;
        nestedShelves = nestedShelves_;
    }
    public String getTitle() {
        return title;
    }

    public ArrayList<PostData> getPosts() {
        return posts;
    }

    public ArrayList<ShelfItem> getShelves() {
        return nestedShelves;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + "\n");
        for (PostData p : posts) {
            sb.append(p.getTitle() + " ");
        }
        sb.append("\n");
        for (ShelfItem i : nestedShelves) {
            sb.append(i.toString());
        }

        return sb.toString();
    }
}
