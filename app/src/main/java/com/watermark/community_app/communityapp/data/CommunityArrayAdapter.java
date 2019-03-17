package com.watermark.community_app.communityapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.watermark.community_app.communityapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blake on 3/15/2019.
 */
public class CommunityArrayAdapter extends ArrayAdapter<ShelfItem> implements Filterable {
    private List<ShelfItem> list = new ArrayList<>();
    private LayoutInflater inflater;

    public CommunityArrayAdapter(Context context, ArrayList<ShelfItem> items) {
        super(context, R.layout.item);
        list = items;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public ShelfItem getItem(int position) {
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);

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
