package edu.gatech.seclass.glm.presenter;


import android.content.Context;
import android.util.Log;

import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;
import edu.gatech.seclass.glm.repository.impl.sql.GrocerySetRepositorySQLImpl;
import edu.gatech.seclass.glm.view.AddGrocerySetView;

/**
 * Represents the presenter implementation for Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class AddGrocerySetPresenterImpl implements AddGrocerySetPresenter {

    private AddGrocerySetView addGrocerySetView;
    private GrocerySetRepository grocerySetRepository;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    public AddGrocerySetPresenterImpl(final Context context, final AddGrocerySetView addGrocerySetView) {
        this.addGrocerySetView = addGrocerySetView;
        this.grocerySetRepository = new GrocerySetRepositorySQLImpl(context);
    }

    @Override
    public void addGrocery(final GrocerySet grocerySet) {
        if (grocerySetRepository != null && grocerySet != null) {
            Log.d(LOG_CLASS, String.format("Adding new grocery set, %s", grocerySet.getName()));
            long id = grocerySetRepository.addUpdateGrocerySet(grocerySet);
            if (id < 0) {
                Log.d(LOG_CLASS, String.format("Could not add new grocery set, %s", grocerySet.getName()));
            }
        }
    }

    @Override
    public GrocerySet getGrocery(final long id) {
        if (grocerySetRepository != null) {
            Log.d(LOG_CLASS, String.format("Get grocery set for id, %d", id));
            return grocerySetRepository.findGrocerySetById(id);
        }
        return null;
    }
}