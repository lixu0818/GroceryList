package edu.gatech.seclass.glm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.crosscuttting.validation.ValidateGrocerySet;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.presenter.AddGrocerySetPresenter;
import edu.gatech.seclass.glm.presenter.AddGrocerySetPresenterImpl;

/**
 * Represents the add view for a {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class AddGrocerySetActivity extends AppCompatActivity implements AddGrocerySetView, View.OnClickListener {

    private EditText groceryName;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private AddGrocerySetPresenter addGrocerySetPresenter;

    public AddGrocerySetActivity() {

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_set);
        addGrocerySetPresenter = new AddGrocerySetPresenterImpl(getApplicationContext(), this);

        Bundle bundle = getIntent().getExtras();
        long groceryId = 0;
        if (bundle != null) {
            groceryId = bundle.getLong("grocery_id");
        }
        Log.e(LOG_CLASS, String.format("Selected grocery id is, %d", groceryId));

        groceryName = (EditText) findViewById(R.id.groceryName);
        findViewById(R.id.addNewGroceryButton).setOnClickListener(this);

        if (groceryId > 0) {
            GrocerySet grocerySet = addGrocerySetPresenter.getGrocery(groceryId);
            groceryName.setText(grocerySet.getName());
        }

        final Button viewGroceryListButton = (Button)findViewById(R.id.addGroceryViewGroceryListButton);
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
    }

    @Override
    public void onClick(final View view) {
        GrocerySet grocerySet = null;
        final Bundle bundle = getIntent().getExtras();
        long groceryId = 0;
        if (bundle != null) {
            groceryId = bundle.getLong("grocery_id");
        }

        if (groceryId > 0) {
            grocerySet = new GrocerySet(groceryId, groceryName.getText().toString());
        } else {
            grocerySet = new GrocerySet(groceryName.getText().toString());
        }
        Log.e(LOG_CLASS, String.format("Selected grocery id is, %d", groceryId));
        Class destinationClazz = null;
        if (ValidateGrocerySet.get().validate(grocerySet)){
            destinationClazz = ViewGrocerySetActivity.class;
            addGrocerySetPresenter.addGrocery(grocerySet);
        } else {
            destinationClazz = AddGrocerySetActivity.class;
            Toast.makeText(AddGrocerySetActivity.this, R.string.toast_add_grocer_set,
                    Toast.LENGTH_SHORT).show();
        }
        try {
            final Intent intent = new Intent(AddGrocerySetActivity.this, destinationClazz);
            startActivity(intent);
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}