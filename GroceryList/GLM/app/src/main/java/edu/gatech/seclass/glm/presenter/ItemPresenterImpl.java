package edu.gatech.seclass.glm.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;
import edu.gatech.seclass.glm.repository.ItemRepository;
import edu.gatech.seclass.glm.repository.impl.sql.GrocerySetRepositorySQLImpl;
import edu.gatech.seclass.glm.repository.impl.sql.ItemRepositorySQLImpl;
import edu.gatech.seclass.glm.view.ViewGroceryItemsView;

/**
 * Represents the presenter for View {@link GroceryItem} and {@link Item}
 *
 * @author Team 02
 * @since 10/13/2016
 * @version 1.0
 */
public class ItemPresenterImpl implements ItemPresenter {

    private ViewGroceryItemsView viewGroceryItemsView;
    private ItemRepository itemRepository;
    private GrocerySetRepository grocerySetRepository;

    /**
     * Tag text for logging.
     */
    private final String LOG_CLASS = getClass().getName();

    public ItemPresenterImpl(final Context context, final ViewGroceryItemsView viewGroceryItemsView) {
        this.viewGroceryItemsView = viewGroceryItemsView;
        this.itemRepository = new ItemRepositorySQLImpl(context);
        this.grocerySetRepository = new GrocerySetRepositorySQLImpl(context);
    }

    @Override
    public List<GroceryItem> listItemsForGrocery(long id) {
        if (itemRepository != null) {
            Log.d(LOG_CLASS, String.format("Get all grocery items"));
            return this.itemRepository.listItemsForGrocery(id);
        }
        return null;
    }

    @Override
    public void removeGroceryItems(List<GroceryItem> groceryItems) {
        if (itemRepository != null) {
            Log.d(LOG_CLASS, String.format("Removing %d grocery items", groceryItems.size()));
            for (GroceryItem item : groceryItems) {
                this.grocerySetRepository.removeGroceryItem(item);
            }
        }
    }

    @Override
    public void checkOffGroceryItems(List<GroceryItem> groceryItems) {
        if (itemRepository != null) {
            Log.d(LOG_CLASS, String.format("Checkoff %d grocery items", groceryItems.size()));
            for (GroceryItem item : groceryItems) {
                this.grocerySetRepository.checkoffGroceryItem(item);
            }
        }
    }
}