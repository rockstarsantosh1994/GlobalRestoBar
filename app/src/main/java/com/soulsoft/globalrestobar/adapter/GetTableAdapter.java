package com.soulsoft.globalrestobar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.model.table.GetTableDataBO;

import java.util.ArrayList;

public class GetTableAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<GetTableDataBO> getTableDataArrayList;

    private ArrayList<GetTableDataBO> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    public GetTableAdapter(Context context, ArrayList<GetTableDataBO> getTableDataArrayList) {
        this.context = context;
        this.getTableDataArrayList = getTableDataArrayList;
    }

    @Override
    public int getCount() {
        return getTableDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getTableDataArrayList.get(position).getTABLENO();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_getallcaptain_row, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text1.setText(getTableDataArrayList.get(position).getTABLENO());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public static class ViewHolder{

        TextView text1;
        ViewHolder(View view){
            text1=view.findViewById(R.id.autoText);
        }
    }

    public void updateData(Context context, ArrayList<GetTableDataBO> data) {
        this.context=context;
        this.getTableDataArrayList = data;
        notifyDataSetChanged();
    }

    /**
     * Our Custom Filter Class.
     */
    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            suggestions.clear();

            if (getTableDataArrayList != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < getTableDataArrayList.size(); i++) {
                    if (getTableDataArrayList.get(i).getTABLENO().toLowerCase().contains(constraint)|| getTableDataArrayList.get(i).getTABLENO().toUpperCase().contains(constraint) ) { // Compare item in original list if it contains constraints.
                        suggestions.add(getTableDataArrayList.get(i)); // If TRUE add item in Suggestions.
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

