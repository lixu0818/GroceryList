package edu.gatech.seclass.glm.repository.impl.memory;

import java.util.List;

import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.repository.ItemRepository;

/**
 * In-memory data access for {@link ItemRepository} for tests.
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public class ItemRepositoryMemoryImpl implements ItemRepository {

    @Override
    public List<ItemType> listAllItemTypes() {
        return null;
    }

    @Override
    public List<Item> listAllItems() {
        return null;
    }

    @Override
    public List<Item> listAllItemsByItemType(final String type) {
        return null;
    }

    @Override
    public List<Item> listAllItemsByRegex(final String regex) {
        return null;
    }

    @Override
    public boolean updateItem(final Item item) {
        return false;
    }

    @Override
    public boolean addItem(final Item item) {
        return false;
    }

    @Override
    public boolean removeItem(Item item) {
        return false;
    }
}
