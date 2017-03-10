package edu.gatech.seclass.glm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GroceryItem;

/**
 * Displays the list view for the grocery items in {@link ViewGroceryItemsActivity}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public class GroceryItemListAdapter extends ArrayAdapter<GroceryItem> {

    private Context context;
    protected List<GroceryItem> items;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();


    public GroceryItemListAdapter(Context context, int resource, List<GroceryItem> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final GroceryItem item = items.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grocery_item_row, null);

        final TextView itemTextView = (TextView) view.findViewById(R.id.view_grocery_item_name_list_row_text);
        if (item.getItem().getItemType() != null && item.getItem().getItemType().getType() != null) {
            itemTextView.setText(item.getItem().getName()+" ("+item.getItem().getItemType().getType()+"), ");
        } else {
            itemTextView.setText(item.getItem().getName()+", ");
        }
        if (item.isCheckoff()) {
            itemTextView.setPaintFlags(itemTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        itemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(context, AddGroceryItemActivity.class);
                    intent.putExtra("grocery_id",
                            ((Activity) context).getIntent().getExtras().getLong("grocery_id"));
                    intent.putExtra("grocery_item_id", item.getId());
                    context.startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final TextView quantityTextView = (TextView) view.findViewById(R.id.view_grocery_item_quantity_list_row_text);
        Log.d(LOG_CLASS, String.format("The quantity is: %f", item.getQuantity()));
        String quantityText = String.valueOf(item.getQuantity());
        Float decimalQuantity = Float.parseFloat(quantityText);
        if (decimalQuantity.floatValue() - decimalQuantity.intValue() == 0) {
            quantityTextView.setText(String.valueOf(decimalQuantity.intValue()));
        } else {
            quantityTextView.setText(quantityText);
        }

        if (item.isCheckoff()) {
            quantityTextView.setPaintFlags(quantityTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        final TextView quantityTypeTextView = (TextView) view.findViewById(R.id.view_grocery_item_quantity_type_list_row_text);
        if (item.getQuantityType().getType() != null) {
            quantityTypeTextView.setText(item.getQuantityType().getType().toLowerCase());
        }
        if (item.isCheckoff()) {
            quantityTypeTextView.setPaintFlags(quantityTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        final CheckBox groceryItemCheckbox = (CheckBox) view.findViewById(R.id.view_grocery_item_list_row_checkbox);
        groceryItemCheckbox.setTag(position);
        groceryItemCheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View parentView = ((View)v.getParent().getParent());
                final ListView listview = (ListView)(parentView.findViewById(R.id.list_view_grocery_items));
                CheckBox cb = (CheckBox)v;
                boolean state = cb.isChecked();
                cb.setChecked(state);
                Log.d(LOG_CLASS, String.format("Checkbox is checked : %b", state));
                if (listview != null) {
                    int checkBoxPosition = Integer.parseInt(cb.getTag().toString());
                    Log.d(LOG_CLASS, String.format("Setting grocery item list view checked at position: %d to state: %b",
                            checkBoxPosition, state));
                    listview.setItemChecked(checkBoxPosition, state);
                }
            }
        });
        return view;
    }
}