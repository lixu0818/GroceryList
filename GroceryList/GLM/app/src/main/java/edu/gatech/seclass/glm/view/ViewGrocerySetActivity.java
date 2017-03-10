package edu.gatech.seclass.glm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.presenter.ViewGrocerySetPresenter;
import edu.gatech.seclass.glm.presenter.ViewGrocerySetPresenterImpl;

/**
 * Represents the view for a {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class ViewGrocerySetActivity extends AppCompatActivity implements ViewGrocerySetView {

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private ViewGrocerySetPresenter viewGrocerySetPresenter;
    private ArrayAdapter<GrocerySet> listAdapter;
    private ListView listview;
    private EditText groceryName;


    public ViewGrocerySetActivity() {
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery_set);

        viewGrocerySetPresenter = new ViewGrocerySetPresenterImpl(getApplicationContext(), this);
        listview = (ListView) findViewById(R.id.list_view_grocery_set);
        groceryName = (EditText) findViewById(R.id.groceryName);

        final List<GrocerySet> grocerySets = viewGrocerySetPresenter.listAllGrocerySets();

        if (grocerySets != null) {
            Log.d(LOG_CLASS, String.format("Viewing: %d groceries", grocerySets.size()));

            listAdapter = new GrocerySetListAdapter(this, R.layout.grocery_set_row, grocerySets);
            listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);
            listview.setAdapter(listAdapter);

        }

        Button addGroceryButton = (Button)findViewById(R.id.addNewGroceryButton);
        addGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(ViewGrocerySetActivity.this, AddGrocerySetActivity.class);
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        Button removeGroceryButton = (Button)findViewById(R.id.removeGroceryButton);
        removeGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GrocerySet> grocerySetsToRemove = new ArrayList<GrocerySet>();
                try{
                    Log.i(LOG_CLASS, String.format("Checked count: %d ", listview.getCheckedItemCount()));
                    SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                    if (checkedItems != null) {
                        for (int i = 0; i < listview.getChildCount(); i++) {
                            if (checkedItems.get(i)) {
                                GrocerySet grocerySet = (GrocerySet)listview.getItemAtPosition(i);
                                Log.d(LOG_CLASS, String.format("GrocerySet to remove: %s", grocerySet));
                                grocerySetsToRemove.add(grocerySet);
                            }
                        }
                    }
                    if (grocerySetsToRemove.size() > 0) {
                        viewGrocerySetPresenter.removeGrocerySets(grocerySetsToRemove);
                    }
                    try{
                        final Intent intent = new Intent(v.getContext(), ViewGrocerySetActivity.class);
                        startActivity(intent);
                    } catch(final Exception e) {
                        Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        Button renameGroceryButton = (Button)findViewById(R.id.renameGroceryButton);
        renameGroceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrocerySet grocerySet = null;
                try{
                    SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                    int renamePosition = 0;
                    int renameCount = 0;
                    for (int i = 0; i < listview.getChildCount(); i++) {
                        if (checkedItems.get(i)) {
                            renamePosition = i;
                            renameCount++;
                        }
                    }

                    if (renameCount > 1) {
                        Toast.makeText(getApplication().getBaseContext(), R.string.rename_only_one, Toast.LENGTH_SHORT).show();
                    } else {
                        grocerySet = (GrocerySet)listview.getItemAtPosition(renamePosition );
                        Log.d(LOG_CLASS, String.format("Grocery set to rename: %s", grocerySet.toString()));
                        if (grocerySet != null) {
                            try{
                                final Intent intent = new Intent(v.getContext(), AddGrocerySetActivity.class);
                                intent.putExtra("grocery_id", grocerySet.getId());
                                startActivity(intent);
                            } catch(final Exception e) {
                                Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }
}