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

import edu.gatech.seclass.glm.domain.QuantityType;

/**
 * Auto complete adapter for {@link QuantityType}
 *
 * @author Team 02
 * @since 10/19/2016
 * @version 1.0
 */
public class QuantityTypeAutoCompleteArrayAdapter extends ArrayAdapter<String> implements Filterable {

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private Context context;
    int resource;

    private List<QuantityType> quantityTypesSearchedByRegex;

    public QuantityTypeAutoCompleteArrayAdapter(Context context, int resource,
                                                List<QuantityType> quantityTypesSearchedByRegex) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.quantityTypesSearchedByRegex = quantityTypesSearchedByRegex;
    }

    @Override
    public int getCount() {
        if (quantityTypesSearchedByRegex != null) {
            return quantityTypesSearchedByRegex.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (quantityTypesSearchedByRegex != null) {
            return quantityTypesSearchedByRegex.get(position).getType();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (quantityTypesSearchedByRegex != null) {
            QuantityType type = quantityTypesSearchedByRegex.get(position);
            if (type != null) {
                return type.getId();
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
            view.setText(quantityTypesSearchedByRegex.get(position).getType());
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
                    filterResults.values = quantityTypesSearchedByRegex;
                    filterResults.count = quantityTypesSearchedByRegex.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    quantityTypesSearchedByRegex = (List<QuantityType>)results.values;
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
     * Get list of {@link QuantityType}
     *
     * @return the list of {@link QuantityType}
     */
    public List<QuantityType> getQuantityTypesSearchedByRegex() {
        return quantityTypesSearchedByRegex;
    }

    /**
     * Set the list of {@link QuantityType}
     * @param quantityTypesSearchedByRegex the regex
     */
    public void setQuantityTypesSearchedByRegex(final List<QuantityType> quantityTypesSearchedByRegex) {
        this.quantityTypesSearchedByRegex = quantityTypesSearchedByRegex;
    }
}