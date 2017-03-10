package edu.gatech.seclass.glm.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.domain.QuantityType;
import edu.gatech.seclass.glm.presenter.AddGroceryItemPresenter;
import edu.gatech.seclass.glm.presenter.AddGroceryItemPresenterImpl;

/**
 * Represents the add view for a {@link edu.gatech.seclass.glm.domain.Item}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class AddGroceryItemActivity extends AppCompatActivity implements AddGroceryItemView {

    private AutoCompleteTextView itemNameAutoCompleteText;
    private EditText quantityText;
    private AutoCompleteTextView quantityUnitAutoCompleteText;
    private AutoCompleteTextView itemTypeAutoCompleteText;

    private AddGroceryItemPresenter addGroceryItemPresenter;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    public AddGroceryItemActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_item);

        addGroceryItemPresenter = new AddGroceryItemPresenterImpl(getApplicationContext(),
                this);

        final Bundle extras = getIntent().getExtras();

        itemNameAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.add_item_name_text);

        final List<Item> itemsByRegex = addGroceryItemPresenter.getItemsByRegex(itemNameAutoCompleteText.getText().toString());
        Log.d(LOG_CLASS,
                String.format("Listing %d items in items auto-complete", itemsByRegex.size()));

        final ItemAutoCompleteArrayAdapter itemsByRegexAdapter =
                new ItemAutoCompleteArrayAdapter(this.getApplicationContext(),
                        android.R.layout.simple_list_item_1, itemsByRegex);
        itemNameAutoCompleteText.setAdapter(itemsByRegexAdapter);
        itemNameAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(LOG_CLASS, String.format("Auto-complete text changed, %s", s.toString()));
                if (s != null) {
                    itemsByRegexAdapter.setItemsSearchedByRegex(
                            addGroceryItemPresenter.getItemsByRegex(s.toString()));
                    itemsByRegexAdapter.notifyChange();
                }
            }
        });

        itemNameAutoCompleteText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (itemNameAutoCompleteText != null && itemNameAutoCompleteText.getText() != null &&
                    itemNameAutoCompleteText.getText().toString() != null) {
                    Log.d(LOG_CLASS,
                        String.format("Focus change on item name text, %s",
                                            itemNameAutoCompleteText.getText().toString()));
                    final Item item = new Item(0, itemNameAutoCompleteText.getText().toString().toLowerCase());
                    final ItemType type = addGroceryItemPresenter.getItemTypeForItem(item);
                    if (type != null) {
                        Log.d(LOG_CLASS, String.format("Found item type, %s", type.toString()));
                        itemTypeAutoCompleteText.setText(type.getType());
                    }
                }
            }
        });

        quantityText = (EditText) findViewById(R.id.add_item_quantity_text);
        quantityUnitAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.add_item_quantity_type_text);
        final List<QuantityType> quantityTypesByRegex =
                addGroceryItemPresenter.getQuantityTypesByRegex(quantityUnitAutoCompleteText.getText().toString());
        Log.d(LOG_CLASS,
                String.format("Listing %d quantity types in quantity type auto-complete", quantityTypesByRegex.size()));
        final QuantityTypeAutoCompleteArrayAdapter quantityTypesByRegexAdapter =
                new QuantityTypeAutoCompleteArrayAdapter(this.getApplicationContext(),
                        android.R.layout.simple_list_item_1, quantityTypesByRegex);
        quantityUnitAutoCompleteText.setAdapter(quantityTypesByRegexAdapter);
        quantityUnitAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(LOG_CLASS, String.format("Auto-complete text changed, %s", s.toString()));
                if (s != null) {
                    quantityTypesByRegexAdapter.setQuantityTypesSearchedByRegex(
                            addGroceryItemPresenter.getQuantityTypesByRegex(s.toString()));
                    quantityTypesByRegexAdapter.notifyChange();
                }
            }
        });

        itemTypeAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.add_item_type_text);
        final List<ItemType> itemTypeByRegex =
                addGroceryItemPresenter.getItemTypesByRegex(itemTypeAutoCompleteText.getText().toString());
        Log.d(LOG_CLASS,
                String.format("Listing %d item types in item type auto-complete", itemTypeByRegex.size()));
        final ItemTypeAutoCompleteArrayAdapter itemTypesByRegexAdapter =
                new ItemTypeAutoCompleteArrayAdapter(this.getApplicationContext(),
                        android.R.layout.simple_list_item_1, itemTypeByRegex);
        itemTypeAutoCompleteText.setAdapter(itemTypesByRegexAdapter);
        itemTypeAutoCompleteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(LOG_CLASS, String.format("Auto-complete text changed, %s", s.toString()));
                if (s != null) {
                    itemTypesByRegexAdapter.setItemTypesSearchedByRegex(
                            addGroceryItemPresenter.getItemTypesByRegex(s.toString()));
                    itemTypesByRegexAdapter.notifyChange();
                }
            }
        });

        final long groceryItemId = extras.getLong("grocery_item_id");

        GroceryItem groceryItem = null;
        if (groceryItemId > 0) {
            itemNameAutoCompleteText.setFocusable(false);
            itemNameAutoCompleteText.setTextColor(Color.LTGRAY);
            itemTypeAutoCompleteText.setFocusable(false);
            itemTypeAutoCompleteText.setTextColor(Color.LTGRAY);
            groceryItem = addGroceryItemPresenter.getGroceryItemById(groceryItemId);
            Log.d(LOG_CLASS, String.format("Found grocery item %s", groceryItem));

            if (groceryItem != null) {
                final Item item = groceryItem.getItem();
                if (item != null) {
                    itemNameAutoCompleteText.setText(item.getName());
                    if (item.getItemType() != null) {
                        itemTypeAutoCompleteText.setText(item.getItemType().getType());
                    }
                }
                quantityText.setText(String.valueOf(groceryItem.getQuantity()));
                if (groceryItem.getQuantityType() != null) {
                    quantityUnitAutoCompleteText.setText(groceryItem.getQuantityType().getType());
                }
            }
        }

        final Button viewGroceryItemsButton = (Button)findViewById(R.id.viewGroceryItemsButton);
        viewGroceryItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final Intent intent = new Intent(v.getContext(), ViewGroceryItemsActivity.class);
                    intent.putExtra("grocery_id", extras.getLong("grocery_id"));
                    startActivity(intent);
                } catch(final Exception e) {
                    Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        final Button addGroceryItemButton = (Button)findViewById(R.id.add_item);
        addGroceryItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_CLASS, "Saving item");
                if (quantityText == null || quantityText.getText().toString() == null ||
                        quantityText.getText().toString().isEmpty() ||
                        Float.valueOf(quantityText.getText().toString()) <= 0) {
                    Log.d(LOG_CLASS,
                            String.format("Quantity: %s is empty or is less than zero.", quantityText.getText().toString()));
                    Toast.makeText(getApplication().getBaseContext(), R.string.valid_quantity, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (addGroceryItemPresenter != null) {
                            if (itemNameAutoCompleteText != null && itemTypeAutoCompleteText != null &&
                                    itemNameAutoCompleteText.getText() != null) {
                                addGroceryItemPresenter.addItem(itemNameAutoCompleteText.getText().toString(),
                                        itemTypeAutoCompleteText.getText().toString());
                            }
                            if (itemNameAutoCompleteText != null && itemTypeAutoCompleteText != null &&
                                    quantityText != null && quantityUnitAutoCompleteText != null &&
                                    itemNameAutoCompleteText.getText() != null &&
                                    itemTypeAutoCompleteText.getText() != null &&
                                    quantityText.getText() != null && quantityUnitAutoCompleteText.getText() != null) {
                                addGroceryItemPresenter.addUpdateGroceryItem(groceryItemId,
                                        extras.getLong("grocery_id"),
                                        itemNameAutoCompleteText.getText().toString(),
                                        itemTypeAutoCompleteText.getText().toString(),
                                        quantityText.getText().toString(),
                                        quantityUnitAutoCompleteText.getText().toString());
                            }
                        }
                        final Intent intent = new Intent(v.getContext(), ViewGroceryItemsActivity.class);
                        intent.putExtra("grocery_id", extras.getLong("grocery_id"));
                        startActivity(intent);
                    } catch(final Exception e) {
                        Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}