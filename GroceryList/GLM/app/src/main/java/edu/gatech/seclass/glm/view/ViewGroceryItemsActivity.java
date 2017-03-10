package edu.gatech.seclass.glm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.presenter.ItemPresenter;
import edu.gatech.seclass.glm.presenter.ItemPresenterImpl;

/**
 * Represents the view for {@link edu.gatech.seclass.glm.domain.GroceryItem}
 *
 * @author Team 02
 * @since 10/14/2016
 * @version 1.0
 */
public class ViewGroceryItemsActivity extends AppCompatActivity implements ViewGroceryItemsView {

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private ItemPresenter itemPresenter;
    private ArrayAdapter<GroceryItem> listAdapter;

    public ViewGroceryItemsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery_items);

        itemPresenter = new ItemPresenterImpl(getApplicationContext(), this);

        final Bundle extras = getIntent().getExtras();

        long groceryId = -1;
        if (extras != null) {
            groceryId = extras.getLong("grocery_id");
            String groceryName = extras.getString("grocery_name");
                Log.i(LOG_CLASS, String.format("Get items for grocery id, name: %d, %s",
                    groceryId, groceryName));
            if (groceryName != null) {
                final TextView groceryNameTextView = (TextView)
                        findViewById(R.id.view_grocery_items_grocery_set_name);
                groceryNameTextView.setText(
                        getApplicationContext().getString(R.string.view_grocery_name_grocery_items, groceryName));
            }
        }

        final ListView listview = (ListView) findViewById(R.id.list_view_grocery_items);
        List<GroceryItem> items = itemPresenter.listItemsForGrocery(groceryId);

        if (items != null) {
            Log.d(LOG_CLASS, String.format("Viewing: %d items", items.size()));
            listAdapter = new GroceryItemListAdapter(this, R.layout.grocery_set_row, items);
            listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
            listview.setAdapter(listAdapter);
        }

        final Button viewGroceryListButton = (Button)findViewById(R.id.viewGroceryListButton);
        viewGroceryListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(v.getContext(), ViewGrocerySetActivity.class);
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final Button addGroceryItemButton = (Button)findViewById(R.id.addGroceryItemButton);
        addGroceryItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(v.getContext(), AddGroceryItemActivity.class);
                    intent.putExtra("grocery_id", extras.getLong("grocery_id"));
                    intent.putExtra("grocery_name",extras.getString("grocery_name"));
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final Button removeGroceryItemButton = (Button)findViewById(R.id.removeGroceryItemButton);
        removeGroceryItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
                Log.i(LOG_CLASS, String.format("Checked count: %d ", listview.getCheckedItemCount()));
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                if (checkedItems != null) {
                    for (int i = 0; i < listview.getChildCount(); i++) {
                        if (checkedItems.get(i)) {
                            GroceryItem groceryItem = (GroceryItem)listview.getItemAtPosition(i);
                            Log.d(LOG_CLASS, String.format("GroceryItem to remove: %s", groceryItem));
                            groceryItems.add(groceryItem);
                        }
                    }
                }
                if (groceryItems.size() > 0) {
                    itemPresenter.removeGroceryItems(groceryItems);
                }
                try{
                    final Intent intent = new Intent(v.getContext(), ViewGroceryItemsActivity.class);
                    intent.putExtra("grocery_id", extras.getLong("grocery_id"));
                    intent.putExtra("grocery_name",extras.getString("grocery_name"));
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final Button checkOffGroceryItemButton = (Button)findViewById(R.id.checkOffGroceryItemButton);
        checkOffGroceryItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
                Log.i(LOG_CLASS, String.format("Checked count: %d ", listview.getCheckedItemCount()));
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                if (checkedItems != null) {
                    for (int i = 0; i < listview.getChildCount(); i++) {
                        if (checkedItems.get(i)) {
                            GroceryItem groceryItem = (GroceryItem)listview.getItemAtPosition(i);
                            Log.d(LOG_CLASS,
                                    String.format("Setting grocery item %s checked off to %b ",
                                            groceryItem, !groceryItem.isCheckoff()));
                            if (groceryItem.isCheckoff()) {
                                groceryItem.setCheckoff(false);
                            } else {
                                groceryItem.setCheckoff(true);
                            }
                            Log.d(LOG_CLASS, String.format("GroceryItem to check off: %s", groceryItem));
                            groceryItems.add(groceryItem);
                        }
                    }
                }
                if (groceryItems.size() > 0) {
                    itemPresenter.checkOffGroceryItems(groceryItems);
                }
                try{
                    final Intent intent = new Intent(v.getContext(), ViewGroceryItemsActivity.class);
                    intent.putExtra("grocery_id", extras.getLong("grocery_id"));
                    intent.putExtra("grocery_name",extras.getString("grocery_name"));
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
