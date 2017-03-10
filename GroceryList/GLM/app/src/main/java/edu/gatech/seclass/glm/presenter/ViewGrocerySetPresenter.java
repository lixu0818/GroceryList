package edu.gatech.seclass.glm.presenter;


import java.util.List;

import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Represents the presenter for View {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public interface ViewGrocerySetPresenter {

    List<GrocerySet> listAllGrocerySets();

    boolean removeGrocerySets(List<GrocerySet> sets);

    boolean renameGrocerySet(GrocerySet grocerySet);
}
