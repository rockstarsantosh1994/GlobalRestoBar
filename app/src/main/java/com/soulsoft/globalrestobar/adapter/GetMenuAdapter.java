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
import com.soulsoft.globalrestobar.model.menucard.MenuDataBO;

import java.util.ArrayList;

public class GetMenuAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<MenuDataBO> menuDataBOS;

    private ArrayList<MenuDataBO> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    public GetMenuAdapter(Context context, ArrayList<MenuDataBO> menuDataBOS) {
        this.context = context;
        this.menuDataBOS = menuDataBOS;
    }

    @Override
    public int getCount() {
        return menuDataBOS.size();
    }

    @Override
    public Object getItem(int position) {
        return menuDataBOS.get(position).getITEMNAME();
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

        holder.text1.setText(menuDataBOS.get(position).getITEMNAME());

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

    public void updateData(Context context, ArrayList<MenuDataBO> data) {
        this.context=context;
        this.menuDataBOS = data;
        notifyDataSetChanged();
    }

    /**
     * Our Custom Filter Class.
     */
    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            suggestions.clear();

            if (menuDataBOS != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < menuDataBOS.size(); i++) {
                    if (menuDataBOS.get(i).getITEMNAME().toLowerCase().contains(constraint)|| menuDataBOS.get(i).getITEMNAME().toUpperCase().contains(constraint) ) { // Compare item in original list if it contains constraints.
                        suggestions.add(menuDataBOS.get(i)); // If TRUE add item in Suggestions.
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

