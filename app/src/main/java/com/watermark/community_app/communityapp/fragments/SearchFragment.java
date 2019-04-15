package com.watermark.community_app.communityapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.watermark.community_app.communityapp.ContentManager;
import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.data.PostData;
import com.watermark.community_app.communityapp.data.ShelfItem;

import java.util.ArrayList;

/**
 * Created by Blake on 2/16/2019.
 *
 * The "Search" page.
 */
public class SearchFragment extends Fragment {
    private ContentManager contentManager = ContentManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View search = inflater.inflate(R.layout.search_layout, container, false);

        SearchView searchView = search.findViewById(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchShelves(contentManager.getPantryEntries(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return false;
            }
        });

        return search;
    }

    public ArrayList<ShelfItem> searchShelves(ArrayList<ShelfItem> shelves, String query) {
        ArrayList<ShelfItem> results = new ArrayList<>();
        for (ShelfItem i : shelves) {
            String titleAllCaps = i.getTitle().toUpperCase();
            String searchAllCaps = query.toUpperCase();
            if (titleAllCaps.contains(searchAllCaps)) {
                results.add(i);
            }

            searchShelves(i.getShelves(), query);
            searchPosts(i.getPosts(), query);
        }

        return results;
    }

    public ArrayList<PostData> searchPosts(ArrayList<PostData> posts, String query) {
        ArrayList<PostData> results = new ArrayList<>();
        for (PostData i : posts) {
            String titleAllCaps = i.getTitle().toUpperCase();
            String searchAllCaps = query.toUpperCase();
            if (titleAllCaps.contains(searchAllCaps)) {
                results.add(i);
            }
        }

        return results;
    }
}
