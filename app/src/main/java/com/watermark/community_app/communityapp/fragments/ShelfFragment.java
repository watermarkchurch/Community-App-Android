package com.watermark.community_app.communityapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.activity.MainActivity;
import com.watermark.community_app.communityapp.activity.PostActivity;
import com.watermark.community_app.communityapp.adapters.CommunityArrayAdapter;
import com.watermark.community_app.communityapp.data.PostItem;
import com.watermark.community_app.communityapp.data.ShelfItem;

import java.util.ArrayList;

/**
 * Created by Blake on 3/15/2019.
 */
public class ShelfFragment extends Fragment {
    private ArrayList<ShelfItem> itemList = new ArrayList<>();
    private CommunityArrayAdapter listAdapter = null;
    private View thisView = null;
    private ListView listView = null;
    private Intent postIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postIntent = new Intent(getContext(), PostActivity.class);
        thisView = inflater.inflate(R.layout.shelf_layout, container, false);
        Bundle b = getArguments();
        ShelfItem shelfItem = (ShelfItem) b.getSerializable("key");

        TextView titleView = (TextView) thisView.findViewById(R.id.shelf_title);
        titleView.setText(shelfItem.getTitle());

        LinearLayout postView = (LinearLayout) thisView.findViewById(R.id.post_section);

        for (final PostItem postItem : shelfItem.getPosts()) {
            TextView t = new TextView(getContext());
            t.setText(postItem.getTitle());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 100);
            params.setMargins(10, 10, 10, 10);
            t.setLayoutParams(params);
            t.setBackgroundColor(getResources().getColor(R.color.black));
            t.setTextSize(15);
            t.setTextColor(getResources().getColor(R.color.white));
            t.setPadding(13, 0, 5, 0);
            postView.addView(t);

            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.launchPostActivity(postItem, postIntent, (AppCompatActivity) getActivity());
                }
            });
        }

        itemList = shelfItem.getShelves();

        // Setup the list of items
        listAdapter = new CommunityArrayAdapter(getContext(), itemList, R.layout.item, R.id.title);
        listView = (ListView) thisView.findViewById(R.id.shelf_list);
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
}
