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
import edu.gatech.seclass.glm.domain.ItemType;

/**
 * Auto complete adapter for {@link ItemType}
 *
 * @author Team 02
 * @since 10/19/2016
 * @version 1.0
 */
public class ItemTypeAutoCompleteArrayAdapter extends ArrayAdapter<String> implements Filterable {


    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private Context context;
    int resource;

    private List<ItemType> itemTypesSearchedByRegex;

    public ItemTypeAutoCompleteArrayAdapter(Context context, int resource, List<ItemType> itemsSearchedByRegex) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.itemTypesSearchedByRegex = itemsSearchedByRegex;
    }

    @Override
    public int getCount() {
        if (itemTypesSearchedByRegex != null) {
            return itemTypesSearchedByRegex.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (itemTypesSearchedByRegex != null) {
            return itemTypesSearchedByRegex.get(position).getType();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (itemTypesSearchedByRegex != null) {
            ItemType item = itemTypesSearchedByRegex.get(position);
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
            view.setText(itemTypesSearchedByRegex.get(position).getType());
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
                    filterResults.values = itemTypesSearchedByRegex;
                    filterResults.count = itemTypesSearchedByRegex.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    itemTypesSearchedByRegex = (List<ItemType>)results.values;
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
    public List<ItemType> getItemTypesSearchedByRegex() {
        return itemTypesSearchedByRegex;
    }

    /**
     * Set the list of {@link Item}
     * @param itemTypesSearchedByRegex the regex
     */
    public void setItemTypesSearchedByRegex(final List<ItemType> itemTypesSearchedByRegex) {
        this.itemTypesSearchedByRegex = itemTypesSearchedByRegex;
    }
}