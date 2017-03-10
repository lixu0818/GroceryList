package edu.gatech.seclass.glm.repository;

import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.domain.QuantityType;

/**
 * Repository for {@link edu.gatech.seclass.glm.domain.Item}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 *
 * @see edu.gatech.seclass.glm.domain.Item
 * @see edu.gatech.seclass.glm.domain.ItemType
 */
public interface ItemRepository {

    /**
     * List all {@link edu.gatech.seclass.glm.domain.Item}
     *
     * @return the list of all items
     */
    List<Item> listAllItems();

    /**
     * List all {@link edu.gatech.seclass.glm.domain.ItemType}
     *
     * @return the list of all item types
     */
    List<ItemType> listAllItemTypes();

    /**
     * List all {@link edu.gatech.seclass.glm.domain.Item} for the
     * {@link edu.gatech.seclass.glm.domain.ItemType}
     *
     * @return the list of all items of a type
     */
    List<Item> listAllItemsByItemType(String type);

    /**
     * List all {@link edu.gatech.seclass.glm.domain.Item}
     *
     * @return the list of all items by a regular expression
     */
    List<Item> listAllItemsByRegex(String regex);

    /**
     * Update {@link edu.gatech.seclass.glm.domain.Item}
     *
     * @return true if successful, false otherwise
     */
    boolean updateItem(Item item);

    /**
     * Add {@link edu.gatech.seclass.glm.domain.Item}
     *
     * @return the id of the item
     */
    long addItem(Item item);

    /**
     * Add an item type.
     *
     * @param itemType The item type
     * @return the id of the item type
     */
    long addItemType(ItemType itemType);

    /**
     * Remove {@link edu.gatech.seclass.glm.domain.Item}
     *
     * @return true if successful, false otherwise
     */
    boolean removeItem(Item item);

    /**
     * List all {@link edu.gatech.seclass.glm.domain.GroceryItem} for the
     * selected grocery.
     *
     * @param id the grocery selected
     * @return the list of all items for the grocery
     */
    List<GroceryItem> listItemsForGrocery(long id);

    /**
     * Find the {@link ItemType} by id
     *
     * @param id The item type id
     * @return The {@link ItemType}
     */
    ItemType findItemTypeById(long id);

    /**
     * Find the {@link Item} by id
     *
     * @param id The item id
     * @return The {@link Item}
     */
    Item findItemById(long id);

    /**
     * Find the {@link Item} by name
     *
     * @param name The item name
     * @return The {@link Item}
     */
    Item findItemByName(String name);

    /**
     * Find the {@link ItemType} by type
     *
     * @param type The item type
     * @return The {@link ItemType}
     */
    ItemType findItemTypeByType(String type);

    /**
     * Find the {@link QuantityType} by type
     *
     * @param type The quantity type
     * @return The {@link QuantityType}
     */
    QuantityType findQuantityTypeByType(String type);


    /**
     * Get {@link ItemType} for the {@link Item}
     * @param item the {@link Item}
     * @return the {@link ItemType}
     */
    ItemType findItemTypeForItem(final Item item);

    /**
     * List all {@link QuantityType} by regex
     *
     * @param regex The regex
     * @return The list of all {@link QuantityType} matching the regex
     */
    List<QuantityType> listAllQuantityTypesByRegex(String regex);

    /**
     * List the {@link ItemType}'s by regex
     *
     * @param regex the regex on which to get the {@link ItemType}
     * @return the list of {@link ItemType}
     */
    List<ItemType> listAllItemTypesByRegex(String regex);

    /**
     * Addd a {@link QuantityType}
     *
     * @param quantityType the {@link QuantityType}
     * @return the id for {@link QuantityType}
     */
    long addQuantityType(QuantityType quantityType);

    /**
     * Find the {@link GroceryItem} by id
     *
     * @param id The {@link GroceryItem} id
     * @return The {@link GroceryItem}
     */
    GroceryItem findGroceryItemById(long id);
}