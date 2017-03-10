package edu.gatech.seclass.glm.presenter;

import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.domain.QuantityType;

/**
 * Represents the presenter for Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/14/2016
 * @version 1.0
 */
public interface AddGroceryItemPresenter {

    /**
     * Add an item
     *
     * @param name The item name
     * @return The {@link Item}
     */
    Item addItem(final String name, final String type);

    /**
     * Add a grocery item
     *
     * @param groceryItemId The grocery item id
     * @param grocerySetId The grocery set id
     * @param name The item name
     * @param type The item type
     * @param quantity The quantity
     * @param quantityType The quantity type
     * @return The {@link GroceryItem}
     *
     */
    GroceryItem addUpdateGroceryItem(long groceryItemId, long grocerySetId, final String name, final String type, final String quantity,
                                     final String quantityType);

    /**
     * Get all the item types
     * @return The list of {@link ItemType}
     */
    List<ItemType> getItemTypes();

    /**
     * Get all {@link Item}'s by regex.
     *
     * @param regex The regex on which to get the {@link Item}
     *
     * @return the list of {@link Item}'s
     */
    List<Item> getItemsByRegex(String regex);

    /**
     * List the {@link QuantityType} by regex
     *
     * @param regex the regex on which to get the {@link QuantityType}
     *
     * @return the list of {@link QuantityType}'s
     */
    List<QuantityType> getQuantityTypesByRegex(String regex);

    /**
     * List the {@link ItemType}'s by regex
     *
     * @param regex the regex on which to get the {@link ItemType}
     * @return the list of {@link ItemType}
     */
    List<ItemType> getItemTypesByRegex(String regex);

    /**
     * Get the {@link ItemType} for {@link Item}
     *
     * @param item The {@link Item}
     * @return The {@link ItemType}
     */
    ItemType getItemTypeForItem(Item item);

    /**
     * Add a {@link QuantityType}
     * @param type The {@link QuantityType}
     * @return true if successful, false otherwise
     */
    long addQuantityType(final QuantityType type);


    /**
     * Get the {@link Item}
     * @param id The item id
     * @return The {@link Item}
     */
    GroceryItem getGroceryItemById(final long id);
}
