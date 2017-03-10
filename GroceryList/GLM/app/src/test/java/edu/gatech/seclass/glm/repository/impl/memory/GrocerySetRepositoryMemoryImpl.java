package edu.gatech.seclass.glm.repository.impl.memory;

import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;

/**
 * In-memory data access for {@link GrocerySetRepository} for tests.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class GrocerySetRepositoryMemoryImpl implements GrocerySetRepository {

    @Override
    public List<GrocerySet> listAllGrocerySets() {
        return null;
    }

    @Override
    public boolean updateGrocerySet(final GrocerySet set) {
        return false;
    }

    @Override
    public boolean addGrocerySet(final GrocerySet set) {
        return false;
    }

    @Override
    public boolean removeGrocerySet(final GrocerySet set) {
        return false;
    }

    @Override
    public boolean addGroceryItem(final GroceryItem groceryItem) {
        return false;
    }

    @Override
    public boolean checkoffGroceryItem(final GroceryItem groceryItem) {
        return false;
    }
}
