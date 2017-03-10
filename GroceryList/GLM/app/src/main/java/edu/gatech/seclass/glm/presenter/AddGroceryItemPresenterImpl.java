package edu.gatech.seclass.glm.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.domain.QuantityType;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;
import edu.gatech.seclass.glm.repository.ItemRepository;
import edu.gatech.seclass.glm.repository.impl.sql.GrocerySetRepositorySQLImpl;
import edu.gatech.seclass.glm.repository.impl.sql.ItemRepositorySQLImpl;
import edu.gatech.seclass.glm.view.AddGroceryItemView;

/**
 * Represents the presenter implementation for Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/14/2016
 * @version 1.0
 */
public class AddGroceryItemPresenterImpl implements AddGroceryItemPresenter {

    private AddGroceryItemView addGroceryItemView;
    private ItemRepository itemRepository;
    private GrocerySetRepository grocerySetRepository;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    public AddGroceryItemPresenterImpl(final Context context,
                                       final AddGroceryItemView addGroceryItemView) {
        this.addGroceryItemView = addGroceryItemView;
        this.grocerySetRepository = new GrocerySetRepositorySQLImpl(context);
        this.itemRepository = new ItemRepositorySQLImpl(context);
    }

    @Override
    public long addQuantityType(final QuantityType type) {
        if (itemRepository != null) {
            Log.d(LOG_CLASS, String.format("Adding new quantity type, %s", type.toString()));
            final QuantityType foundQuantityType = itemRepository.findQuantityTypeByType(type.getType());
            if (foundQuantityType == null) {
                return itemRepository.addQuantityType(type);
            } else {
                return foundQuantityType.getId();
            }
        }
        return -1;
    }

    @Override
    public Item addItem(final String name, final String type) {
        long id = -1;
        ItemType itemType = null;
        Item item = null;
        if (itemRepository != null) {
            Log.d(LOG_CLASS, String.format("Adding new item, %s with type, %s", name, type));
            if (type != null) {
                itemType = itemRepository.findItemTypeByType(type);
                if (itemType == null) {
                    itemType = new ItemType(type);
                    id = itemRepository.addItemType(itemType);
                    if (id < 0) {
                        Log.d(LOG_CLASS, String.format("Could not add the item type, %s", type));
                    }
                }
            }

            if (name != null) {
                item = itemRepository.findItemByName(name);
                if (item == null) {
                    item = new Item(name);
                    item.setItemType(itemType);
                    id = itemRepository.addItem(item);
                    if (id < 0) {
                        Log.d(LOG_CLASS, String.format("Could not add the item, %s", name));
                    }
                }
            }
        }
        return item;
    }

    @Override
    public GroceryItem addUpdateGroceryItem(long groceryItemId, long grocerySetId, String name, String type, String quantity, String quantityType) {
        Item item = addItem(name, type);
        GroceryItem groceryItem = null;
        long id = -1;
        if (grocerySetRepository != null) {
            if (groceryItemId <= 0) {
                Log.d(LOG_CLASS, String.format("Adding new grocery item, %s", name));
            } else {
                Log.d(LOG_CLASS, String.format("Updating grocery item, %s", name));
            }
            if (quantity != null && !quantity.equals("")) {
                groceryItem = new GroceryItem();
                groceryItem.setId(groceryItemId);
                Log.d(LOG_CLASS, String.format("The quantity is, %s", quantity));
                groceryItem.setQuantity(Float.valueOf(quantity));
                groceryItem.setGrocerySet(new GrocerySet(grocerySetId));
                groceryItem.setItem(item);
                Log.d(LOG_CLASS, String.format("Set the grocery item to, %s", item.toString()));
                if (quantityType != null && !quantityType.equals("")) {
                    QuantityType unitType = new QuantityType(quantityType);
                    unitType.setId(addQuantityType(unitType));
                    groceryItem.setQuantityType(unitType);
                }
                id = grocerySetRepository.addUpdateGroceryItem(groceryItem);
                if (id < 0) {
                    Log.d(LOG_CLASS,
                            String.format("Could not add new grocery item, %s with quantity %s", name, quantity));
                }
            }
        }
        return groceryItem;
    }


    @Override
    public List<ItemType> getItemTypes() {
        if (itemRepository != null) {
            return itemRepository.listAllItemTypes();
        }
        return null;
    }

    @Override
    public List<Item> getItemsByRegex(String regex) {
        if (itemRepository != null) {
            return itemRepository.listAllItemsByRegex(regex);
        }
        return null;
    }

    @Override
    public ItemType getItemTypeForItem(final Item item) {
        if (itemRepository != null) {
            return itemRepository.findItemTypeForItem(item);
        }
        return null;
    }

    @Override
    public List<QuantityType> getQuantityTypesByRegex(String regex) {
        if (itemRepository != null) {
            return itemRepository.listAllQuantityTypesByRegex(regex);
        }
        return null;
    }

    @Override
    public List<ItemType> getItemTypesByRegex(String regex) {
        if (itemRepository != null) {
            return itemRepository.listAllItemTypesByRegex(regex);
        }
        return null;
    }

    @Override
    public GroceryItem getGroceryItemById(long id) {
        if (itemRepository != null) {
            return itemRepository.findGroceryItemById(id);
        }
        return null;
    }
}
