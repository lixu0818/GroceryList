package edu.gatech.seclass.glm.repository;


import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Repository for {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 *
 * @see edu.gatech.seclass.glm.domain.GrocerySet
 * @see edu.gatech.seclass.glm.domain.GroceryItem
 */
public interface GrocerySetRepository extends Repository {

    /**
     * List all {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return the grocery set list
     */
    List<GrocerySet> listAllGrocerySets();

    /**
     * Update {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return true if successful, false otherwise
     */
    boolean updateGrocerySet(GrocerySet set);

    /**
     * Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return the id
     */
    long addUpdateGrocerySet(GrocerySet set);

    /**
     * Remove {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return true if successful, false otherwise
     */
    boolean removeGrocerySet(long id);

    /**
     * Add a {@link edu.gatech.seclass.glm.domain.GroceryItem}
     * to the {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return id of the item added
     */
    long addUpdateGroceryItem(GroceryItem groceryItem);

    /**
     * Remove {@link edu.gatech.seclass.glm.domain.GroceryItem}
     *
     * @param groceryItem The {@link edu.gatech.seclass.glm.domain.GroceryItem} to remove
     *
     * @return true if successful, false otherwise
     */
    boolean removeGroceryItem(final GroceryItem groceryItem);

    /**
     * Check-off a {@link edu.gatech.seclass.glm.domain.GroceryItem}
     * from the {@link edu.gatech.seclass.glm.domain.GrocerySet}
     *
     * @return true if successful, false otherwise
     */
    boolean checkoffGroceryItem(GroceryItem groceryItem);

    /**
     * Rename {@link GrocerySet}
     * @param grocerySet The grocery set
     * @return The grocery set
     */
    boolean renameGrocerySet(GrocerySet grocerySet);

    /**
     * Find the {@link GrocerySet}
     *
     * @param id The grocery set id
     * @return The {@link GrocerySet}
     */
    GrocerySet findGrocerySetById(long id);
}
