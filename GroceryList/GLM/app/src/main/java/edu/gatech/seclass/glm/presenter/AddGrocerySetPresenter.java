package edu.gatech.seclass.glm.presenter;

import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Represents the presenter for Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public interface AddGrocerySetPresenter {

    /**
     * Add a new {@link GrocerySet}
     *
     * @param grocerySet The {@link GrocerySet}
     */
    void addGrocery(GrocerySet grocerySet);

    /**
     * Get the {@link GrocerySet}
     *
     * @param id The grocery id
     *
     * @return the {@link GrocerySet}
     */
    GrocerySet getGrocery(long id);
}
