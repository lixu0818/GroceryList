package edu.gatech.seclass.glm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.glm.domain.Item;

/**
 * Auto complete adapter for {@link Item}
 *
 * @author Team 02
 * @since 10/19/2016
 * @version 1.0
 */
public class ItemAutoCompleteArrayAdapter extends ArrayAdapter<String> implements Filterable {


    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private Context context;
    int resource;

    private List<Item> itemsSearchedByRegex;

    public ItemAutoCompleteArrayAdapter(Context context, int resource, List<Item> itemsSearchedByRegex) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.itemsSearchedByRegex = itemsSearchedByRegex;
    }

    @Override
    public int getCount() {
        if (itemsSearchedByRegex != null) {
            return itemsSearchedByRegex.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (itemsSearchedByRegex != null) {
            return itemsSearchedByRegex.get(position).getName();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (itemsSearchedByRegex != null) {
            Item item = itemsSearchedByRegex.get(position);
            if (item != null) {
                return item.getId();
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view =  (TextView)convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (TextView)layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            view.setText(itemsSearchedByRegex.get(position).getName());
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = itemsSearchedByRegex;
                    filterResults.count = itemsSearchedByRegex.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    itemsSearchedByRegex = (List<Item>)results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Notify that data has changed.
     */
    public void notifyChange() {
        notifyDataSetChanged();
    }

    /**
     * Get list of {@link Item}
     *
     * @return the list of {@link Item}
     */
    public List<Item> getItemsSearchedByRegex() {
        return itemsSearchedByRegex;
    }

    /**
     * Set the list of {@link Item}
     * @param itemsSearchedByRegex the regex
     */
    public void setItemsSearchedByRegex(final List<Item> itemsSearchedByRegex) {
        this.itemsSearchedByRegex = itemsSearchedByRegex;
    }
}