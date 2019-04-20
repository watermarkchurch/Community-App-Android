package com.watermark.community_app.communityapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.watermark.community_app.communityapp.ContentManager;
import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.data.CommunityArrayAdapter;
import com.watermark.community_app.communityapp.data.ShelfItem;

import java.util.ArrayList;

/**
 * Created by Blake on 2/16/2019.
 *
 * The "Pantry" page.
 */
public class PantryFragment extends Fragment {
    private ContentManager contentManager = ContentManager.getInstance();
    private ArrayList<ShelfItem> itemList = new ArrayList<>();
    private CommunityArrayAdapter listAdapter = null;
    private View thisView = null;
    private ListView listView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.pantry_layout, container, false);

        // Setup the list of items
        listAdapter = new CommunityArrayAdapter(getContext(), itemList);
        listView = (ListView) thisView.findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShelfItem selectedItem = itemList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("key", selectedItem);
                ShelfFragment f = new ShelfFragment();
                f.setArguments(b);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(((ViewGroup)thisView.getParent()).getId(), f).addToBackStack(null).commit();
            }
        });

        return thisView;
    }

    public void pantryContentLoaded() {
        for (ShelfItem i : contentManager.getPantryEntries()) {
            itemList.add(i);

            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
        }
    }
}
