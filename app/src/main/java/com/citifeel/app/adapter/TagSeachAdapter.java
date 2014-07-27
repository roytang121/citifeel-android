package com.citifeel.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.citifeel.app.R;
import com.citifeel.app.model.TagModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roytang on 27/7/14.
 */
public class TagSeachAdapter extends BaseAdapter implements Filterable {
    private ArrayList<TagModel> items;
    private final ArrayList<TagModel> full_items;
    private final Context context;

    public TagSeachAdapter(final Context context, final ArrayList<TagModel> items) {
        this.items = items;
        this.full_items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TagModel getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tagsearch, null);
            holder.tag = (TextView) convertView.findViewById(R.id.tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TagModel model = getItem(position);
        if(model != null) {
            holder.tag.setText(model.tag);
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence query) {
                Log.i("Query", query.toString());
                List<TagModel> filteredResults = new ArrayList<TagModel>();
                for(TagModel tag : full_items) {
                    if(tag.tag.toLowerCase().contains(query.toString().toLowerCase()))
                        filteredResults.add(tag);
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values != null) {
                    items = (ArrayList<TagModel>) results.values;
                    TagSeachAdapter.this.notifyDataSetChanged();
                }
            }

        };
    }

    private class ViewHolder {
        public TextView tag;
    }

}
