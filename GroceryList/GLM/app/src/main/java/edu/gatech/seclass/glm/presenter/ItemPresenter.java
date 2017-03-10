package edu.gatech.seclass.glm.presenter;


import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Represents the presenter for View {@link GrocerySet}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public interface ItemPresenter {

    /**
     * List {@link GroceryItem} for the {@link GrocerySet}
     *
     * @param id The grocery id
     * @return The list of items for the grocery33
     */
    List<GroceryItem> listItemsForGrocery(long id);

    /**
     * Remove {@link GroceryItem} for the {@link GrocerySet}
     *
     * @param groceryItems The {@link GroceryItem}'s to be removed
     */
    void removeGroceryItems(List<GroceryItem> groceryItems);

    /**
     * CheckOff {@link GroceryItem} for the {@link GrocerySet}
     *
     * @param groceryItems The {@link GroceryItem}'s to be removed
     */
    void checkOffGroceryItems(List<GroceryItem> groceryItems);
}
