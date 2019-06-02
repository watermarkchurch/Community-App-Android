package com.watermark.community_app.communityapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.watermark.community_app.communityapp.ContentManager;
import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.activity.MainActivity;
import com.watermark.community_app.communityapp.activity.PostActivity;
import com.watermark.community_app.communityapp.adapters.CommunityArrayAdapter;
import com.watermark.community_app.communityapp.data.PostItem;
import com.watermark.community_app.communityapp.data.ShelfItem;

import java.util.ArrayList;

/**
 * Created by Blake on 2/16/2019.
 *
 * The "Search" page.
 */
public class SearchFragment extends Fragment {
    private ContentManager contentManager = ContentManager.getInstance();

    private ArrayList<ShelfItem> shelfResults = new ArrayList<>();
    private CommunityArrayAdapter shelfAdapter = null;
    private ListView shelfList = null;

    private ArrayList<PostItem> postResults = new ArrayList<>();
    private CommunityArrayAdapter postAdapter = null;
    private ListView postList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View search = inflater.inflate(R.layout.search_layout, container, false);

        // Setup the list of items
        shelfAdapter = new CommunityArrayAdapter(getActivity(), shelfResults, R.layout.search_result, R.id.result);
        shelfList = search.findViewById(R.id.shelfResults);
        shelfList.setAdapter(shelfAdapter);

        postAdapter = new CommunityArrayAdapter(getActivity(), postResults, R.layout.search_result, R.id.result);
        postList = search.findViewById(R.id.postResults);
        postList.setAdapter(postAdapter);
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostItem selectedItem = postResults.get(position);
                Intent postIntent = new Intent(getContext(), PostActivity.class);
                MainActivity.launchPostActivity(selectedItem, postIntent, (AppCompatActivity) getActivity());
            }
        });

        final SearchView searchView = search.findViewById(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                shelfResults.clear();
                postResults.clear();
                shelfAdapter.notifyDataSetChanged();
                postAdapter.notifyDataSetChanged();
                return false;
            }

        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shelfResults.clear();
                postResults.clear();
                searchShelves(contentManager.getPantryEntries(), query, shelfResults, postResults);
                shelfAdapter.notifyDataSetChanged();
                postAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText == null || newText.isEmpty()) {
                    shelfResults.clear();
                    postResults.clear();
                    shelfAdapter.notifyDataSetChanged();
                    postAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return search;
    }

    public void searchShelves(ArrayList<ShelfItem> shelves, String query, ArrayList<ShelfItem> shelfResults, ArrayList<PostItem> postResults) {
        for (ShelfItem i : shelves) {
            String titleAllCaps = i.getTitle().toUpperCase().trim();
            String searchAllCaps = query.toUpperCase().trim();
            if (titleAllCaps.contains(searchAllCaps)) {
                shelfResults.add(i);
            }

            searchShelves(i.getShelves(), query, shelfResults, postResults);
            postResults.addAll(searchPosts(i.getPosts(), query));
        }
    }

    public ArrayList<PostItem> searchPosts(ArrayList<PostItem> posts, String query) {
        ArrayList<PostItem> results = new ArrayList<>();
        for (PostItem i : posts) {
            String titleAllCaps = i.getTitle().toUpperCase().trim();
            String searchAllCaps = query.toUpperCase().trim();
            if (titleAllCaps.contains(searchAllCaps)) {
                results.add(i);
            }
        }

        return results;
    }
}
