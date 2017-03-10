package edu.gatech.seclass.glm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.presenter.ViewGrocerySetPresenter;
import edu.gatech.seclass.glm.presenter.ViewGrocerySetPresenterImpl;

/**
 * Represents the add view for a {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class RenameGrocerySetActivity extends AppCompatActivity implements ViewGrocerySetView, View.OnClickListener {

    private EditText newGroceryName;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    private ViewGrocerySetPresenter viewGrocerySetPresenter;
    private long groceryId;

    public RenameGrocerySetActivity() {
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_grocery_set);

        viewGrocerySetPresenter = new ViewGrocerySetPresenterImpl(getApplicationContext(), this);
        Bundle extras = getIntent().getExtras();
        groceryId = -1;
        if (extras != null) {
            groceryId = extras.getLong("grocerySetsToRename");
            Log.i(LOG_CLASS, String.format("Rename grocery id: %d", groceryId));
        }


        newGroceryName = (EditText) findViewById(R.id.newGroceryName);
        findViewById(R.id.renameGroceryButton).setOnClickListener(this);


    }

    @Override
    public void onClick(final View view) {
        if (newGroceryName.getText().toString() == null ||newGroceryName.getText().toString().isEmpty()){
            Toast.makeText(RenameGrocerySetActivity.this, "Please enter a new name for the grocery list",
                    Toast.LENGTH_LONG).show();
            try {
                final Intent intent = new Intent(RenameGrocerySetActivity.this, RenameGrocerySetActivity.class);
                startActivity(intent);
            } catch (final Exception e) {
                Log.e(LOG_CLASS, String.format("Exception starting intent %s", e.getMessage()));
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else {


            Log.i(LOG_CLASS, String.format("Rename grocery id: %d", groceryId));
            viewGrocerySetPresenter.renameGrocerySet(new GrocerySet(groceryId, newGroceryName.getText().toString()));

            Intent intent = new Intent(RenameGrocerySetActivity.this, ViewGrocerySetActivity.class);
                startActivity(intent);

                    }


    }
}
