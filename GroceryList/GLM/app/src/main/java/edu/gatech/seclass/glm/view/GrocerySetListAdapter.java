package edu.gatech.seclass.glm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Displays the list view for the grocery sets in {@link ViewGrocerySetActivity}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public class GrocerySetListAdapter extends ArrayAdapter<GrocerySet> {

    private Context context;
    protected List<GrocerySet> grocerySets;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();


    public GrocerySetListAdapter(Context context, int resource, List<GrocerySet> grocerySets) {
        super(context, resource, grocerySets);
        this.context = context;
        this.grocerySets = grocerySets;
    }



    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final GrocerySet grocerySet = grocerySets.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grocery_set_row, null);

        final TextView grocerySetTextView = (TextView) view.findViewById(R.id.view_grocery_set_list_row_text);
        grocerySetTextView.setText(grocerySet.getName());
        grocerySetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(context, ViewGroceryItemsActivity.class);
                    intent.putExtra("grocery_id", grocerySet.getId());
                    intent.putExtra("grocery_name", grocerySet.getName());
                    context.startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final CheckBox grocerySetCheckbox = (CheckBox) view.findViewById(R.id.view_grocery_set_list_row_checkbox);
        grocerySetCheckbox.setTag(position);
        grocerySetCheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View parentView = ((View)v.getParent().getParent());
                final ListView listview = (ListView)(parentView.findViewById(R.id.list_view_grocery_set));
                CheckBox cb = (CheckBox)v;
                boolean state = cb.isChecked();
                cb.setChecked(state);
                Log.d(LOG_CLASS, String.format("Checkbox is checked : %b", state));
                if (listview != null) {
                    int checkBoxPosition = Integer.parseInt(cb.getTag().toString());
                    Log.d(LOG_CLASS, String.format("Setting list view checked at position: %d to state: %b", checkBoxPosition, state));
                    listview.setItemChecked(checkBoxPosition, state);
                }
            }
        });

        return view;
    }
}