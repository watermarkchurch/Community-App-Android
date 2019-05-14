package com.watermark.community_app.communityapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.data.ICommunityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blake on 5/12/2019.
 *
 * A generic array adapter that will be used to display the pantry list and search results list
 */
public class CommunityArrayAdapter<T extends ICommunityItem> extends ArrayAdapter<T> implements Filterable {
    private List<T> list;
    private LayoutInflater inflater;
    private int layoutResource;
    private int itemResource;

    public CommunityArrayAdapter(Context context, ArrayList<T> items, int layoutResource_, int itemResource_) {
        super(context, R.layout.item);
        list = items;
        inflater = LayoutInflater.from(context);
        layoutResource = layoutResource_;
        itemResource = itemResource_;
    }

    public int getCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(itemResource);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position).getTitle());

        return convertView;
    }

    static class ViewHolder {
        TextView title;
    }
}
