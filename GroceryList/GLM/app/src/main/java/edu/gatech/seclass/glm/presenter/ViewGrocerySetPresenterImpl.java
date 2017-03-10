package edu.gatech.seclass.glm.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;
import edu.gatech.seclass.glm.repository.impl.sql.GrocerySetRepositorySQLImpl;
import edu.gatech.seclass.glm.view.ViewGrocerySetView;

/**
 * Represents the presenter implementation for View {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public class ViewGrocerySetPresenterImpl implements ViewGrocerySetPresenter {

    private ViewGrocerySetView viewGrocerySetView;
    private GrocerySetRepository grocerySetRepository;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    public ViewGrocerySetPresenterImpl(final Context context, final ViewGrocerySetView viewGrocerySetView) {
        this.viewGrocerySetView = viewGrocerySetView;
        this.grocerySetRepository = new GrocerySetRepositorySQLImpl(context);
    }

    @Override
    public boolean removeGrocerySets(List<GrocerySet> sets) {
        boolean removed = false;
        for (GrocerySet set : sets) {
            Log.d(LOG_CLASS, String.format("Removing grocery id: %d", set.getId()));
            removed &= grocerySetRepository.removeGrocerySet(set.getId());
        }
        return removed;
    }

    @Override
    public List<GrocerySet> listAllGrocerySets() {
        if (grocerySetRepository != null) {
            Log.d(LOG_CLASS, String.format("Get all grocery sets"));
            return grocerySetRepository.listAllGrocerySets();
        }
        return null;
    }
    @Override
    public boolean renameGrocerySet(GrocerySet grocerySet) {
        boolean renamed = false;

            Log.d(LOG_CLASS, String.format("Rename grocery id: %d", grocerySet.getId()));
        renamed &= grocerySetRepository.renameGrocerySet(grocerySet);

        return renamed;
    }
}
