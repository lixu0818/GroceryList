package edu.gatech.seclass.glm.repository.impl.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.Item;
import edu.gatech.seclass.glm.domain.ItemType;
import edu.gatech.seclass.glm.domain.QuantityType;
import edu.gatech.seclass.glm.repository.ItemRepository;

/**
 * SQL data access for {@link edu.gatech.seclass.glm.repository.ItemRepository}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class ItemRepositorySQLImpl extends GrocerySQLiteOpenHelper implements ItemRepository {
    private final String LOG_CLASS = getClass().getName();

    public ItemRepositorySQLImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public ItemType findItemTypeForItem(final Item item) {
        ItemType itemType = null;
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Get item type for item: %s", item.toString()));

                final String query = "SELECT type." + ITEM_TYPE_TABLE_COLUMN_ID + "," +
                        " type."+ ITEM_TYPE_TABLE_COLUMN_NAME +
                        " FROM " + ITEM_TYPE_TABLE_NAME + " type,"
                                 + ITEM_TABLE_NAME + " item " +
                        " WHERE " + "item." + ITEM_TABLE_COLUMN_ITEMTYPE_ID + " = "+
                                    "type." + ITEM_TYPE_TABLE_COLUMN_ID +
                          " AND " + "lower(item." + ITEM_TABLE_COLUMN_NAME + ") = ?";
                Log.d(LOG_CLASS, String.format("Item type for item query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query, new String[] { String.valueOf(item.getName()) });
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        itemType = new ItemType(cursor.getLong(0), cursor.getString(1));
                        Log.d(LOG_CLASS, String.format("Adding item type %s", itemType.toString()));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return itemType;
    }

    @Override
    public List<ItemType> listAllItemTypes() {
        final List<ItemType> itemTypes = new ArrayList<ItemType>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select all item types"));
                cursor = sqLiteDatabase.query(ITEM_TYPE_TABLE_NAME, ITEM_TYPE_ALL_COLUMNS, null,
                        null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final ItemType itemType = new ItemType(cursor.getLong(0), cursor.getString(1));
                        Log.d(LOG_CLASS, String.format("Adding item type %s", itemType.toString()));
                        itemTypes.add(itemType);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return itemTypes;
    }

    @Override
    public List<Item> listAllItems() {
        final List<Item> items = new ArrayList<Item>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select all items"));
                cursor = sqLiteDatabase.query(ITEM_TABLE_NAME, ITEM_ALL_COLUMNS, null,
                        null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final Item item = new Item(cursor.getLong(0), cursor.getString(1));
                        items.add(item);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return items;
    }

    @Override
    public List<Item> listAllItemsByItemType(final String type) {
        final List<Item> items = new ArrayList<Item>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select all item types"));
                cursor = sqLiteDatabase.query(ITEM_TABLE_NAME, ITEM_ALL_COLUMNS,
                        ITEM_TABLE_COLUMN_ITEMTYPE_ID + " = '" + type + "'", null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final Item item = new Item(cursor.getLong(0), cursor.getString(1));
                        items.add(item);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return items;
    }

    @Override
    public List<Item> listAllItemsByRegex(String regex) {
        final List<Item> items = new ArrayList<Item>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                if (regex == null || regex.isEmpty()) {
                    regex = "'%'";
                } else {
                    regex = String.format("'%s%s'", regex, "%");
                }

                final String query = "SELECT " + ITEM_TABLE_COLUMN_ID + ","
                        + ITEM_TABLE_COLUMN_NAME +
                        " FROM " + ITEM_TABLE_NAME +
                        " WHERE lower("+ITEM_TABLE_COLUMN_NAME+") like lower("+regex+")";
                Log.d(LOG_CLASS, String.format("List all items by regex query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final Item item = new Item(cursor.getLong(0), cursor.getString(1));
                        Log.d(LOG_CLASS, String.format("Listing item %s", item.toString()));
                        items.add(item);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return items;
    }

    @Override
    public boolean updateItem(final Item item) {
        long id = -1;
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Update item: %s", item.getName()));
                sqLiteDatabase.beginTransaction();
                final ContentValues values = new ContentValues();
                values.put(ITEM_TABLE_COLUMN_NAME, item.getName());
                int count = sqLiteDatabase.update(ITEM_TABLE_NAME, values, "id=",
                                        new String[] { Long.toString(item.getId()) });
                if (count > 0) {
                    Log.d(LOG_CLASS, String.format("Updated item with id: %d", id));
                    sqLiteDatabase.setTransactionSuccessful();
                }
                return true;
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
                close();
            }
        }
        return false;
    }

    @Override
    public long addItem(final Item item){
        long id = -1;

        ItemType itemType = item.getItemType();
        if (itemType != null) {
            final ItemType foundItemType = findItemTypeByType(itemType.getType());
            if (foundItemType != null) {
                itemType.setId(foundItemType.getId());
            } else {
                itemType.setId(addItemType(itemType));
            }
            Log.d(LOG_CLASS, String.format("Setting item type id as: %d", itemType.getId()));
        }

        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Add new item for %s and item type: %s", item.getName(),
                        item.getItemType().getType()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(ITEM_TABLE_COLUMN_NAME, item.getName());
                if (itemType != null) {
                    initialValues.put(ITEM_TABLE_COLUMN_ITEMTYPE_ID, itemType.getId());
                }
                id = sqLiteDatabase.insertOrThrow(ITEM_TABLE_NAME, null, initialValues);
                if (id > -1) {
                    Log.d(LOG_CLASS, String.format("Inserted item id is: %d", id));
                    sqLiteDatabase.setTransactionSuccessful();
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
                close();
            }
        }
        return id;
    }

    @Override
    public long addItemType(ItemType itemType) {
        long id = -1;
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Add new item type, %s", itemType.getId()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(ITEM_TYPE_TABLE_COLUMN_NAME, itemType.getType());
                id = sqLiteDatabase.insertOrThrow(ITEM_TYPE_TABLE_NAME, null, initialValues);
                if (id > -1) {
                    Log.d(LOG_CLASS, String.format("Inserted item type id: %d", id));
                    sqLiteDatabase.setTransactionSuccessful();
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
                close();
            }
        }
        return id;
    }



    @Override
    public long addQuantityType(QuantityType quantityType) {
        long id = -1;
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Add new quantity type, %s", quantityType.getId()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(QUANTITY_TYPE_TABLE_COLUMN_NAME, quantityType.getType());
                id = sqLiteDatabase.insertOrThrow(QUANTITY_TYPE_TABLE_NAME, null, initialValues);
                if (id > -1) {
                    Log.d(LOG_CLASS, String.format("Inserted quantity type id: %d", id));
                    sqLiteDatabase.setTransactionSuccessful();
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
                close();
            }
        }
        return id;
    }

    @Override
    public boolean removeItem(final Item item) {
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Remove item for %s", item.getName()));
                sqLiteDatabase.beginTransaction();
                int count = sqLiteDatabase.delete(ITEM_TABLE_NAME, "id=?", new String[] { Long.toString(item.getId()) });
                if (count == 1) {
                    Log.d(LOG_CLASS, String.format("Removed item id is: %d", item.getId()));
                    sqLiteDatabase.setTransactionSuccessful();
                    return true;
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.endTransaction();
                close();
            }
        }
        return false;
    }

    @Override
    public List<GroceryItem> listItemsForGrocery(long id) {
        final List<GroceryItem> items = new ArrayList<GroceryItem>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("List all grocery items for id: %d", id));

                final String query = "SELECT "   + ITEM_TABLE_NAME + "."
                                                 + ITEM_TABLE_COLUMN_ID + ", "+
                                                   ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_NAME + ", " +
                                                   GROCERY_ITEM_TABLE_NAME + "." +
                                                   GROCERY_ITEM_TABLE_COLUMN_ID + ", "+
                                                   GROCERY_ITEM_TABLE_NAME + "." +
                                                   GROCERY_ITEM_TABLE_COLUMN_QUANTITY + ", "+
                                                   QUANTITY_TYPE_TABLE_NAME + "." +
                                                   QUANTITY_TYPE_TABLE_COLUMN_NAME + ", "+
                                                   GROCERY_ITEM_TABLE_NAME + "." +
                                                   GROCERY_ITEM_TABLE_COLUMN_CHECKOFF + ", " +
                                                   ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_NAME +
                                      " FROM "   + GROCERY_ITEM_TABLE_NAME +
                                      " LEFT OUTER JOIN " + ITEM_TABLE_NAME + " ON " +
                                    ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_ID + "=" +
                                    GROCERY_ITEM_TABLE_NAME + "." + GROCERY_ITEM_TABLE_COLUMN_ITEMID +
                                      " LEFT OUTER JOIN " + ITEM_TYPE_TABLE_NAME + " ON " +
                                    ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_ID + "=" +
                                    ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_ITEMTYPE_ID +
                                      " LEFT OUTER JOIN " + QUANTITY_TYPE_TABLE_NAME +  " ON " +
                        QUANTITY_TYPE_TABLE_NAME + "." + QUANTITY_TYPE_TABLE_COLUMN_ID + "=" +
                        GROCERY_ITEM_TABLE_NAME + "." + GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID +
                                      " WHERE "  + GROCERY_ITEM_TABLE_NAME + "."
                                                 + GROCERY_ITEM_TABLE_COLUMN_GSETID + "=?" +
                                    " ORDER BY " + ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_NAME + ", "
                                                 + ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_NAME;

                Log.d(LOG_CLASS, String.format("Query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final GroceryItem groceryItem = new GroceryItem(cursor.getLong(2));
                        final Item item = new Item(cursor.getLong(0), cursor.getString(1));
                        groceryItem.setItem(item);
                        groceryItem.setQuantity(cursor.getFloat(3));
                        groceryItem.setQuantityType(new QuantityType(cursor.getString(4)));
                        groceryItem.setCheckoff(cursor.getInt(5) == 1 ? true : false);
                        item.setItemType(new ItemType(cursor.getString(6)));
                        Log.d(LOG_CLASS, groceryItem.toString());
                        items.add(groceryItem);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return items;
    }

    @Override
    public ItemType findItemTypeById(long id) {
        Cursor cursor = null;
        ItemType itemType = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select item type by id: %d", id));
                cursor = sqLiteDatabase.query(ITEM_TYPE_TABLE_NAME, ITEM_TYPE_ALL_COLUMNS,
                                        ITEM_TYPE_TABLE_COLUMN_ID + "=?",
                                        new String[] { String.valueOf(id) }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        itemType = new ItemType(cursor.getLong(0), cursor.getString(1));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return itemType;
    }

    @Override
    public Item findItemById(long id) {
        Cursor cursor = null;
        Item item = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select item by name: %d", id));
                cursor = sqLiteDatabase.query(ITEM_TABLE_NAME, ITEM_ALL_COLUMNS,
                        ITEM_TABLE_COLUMN_NAME + "=?",
                        new String[] { String.valueOf(id) }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        item = new Item(cursor.getLong(0), cursor.getString(1));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return item;
    }

    @Override
    public Item findItemByName(String name) {
        Cursor cursor = null;
        Item item = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select item by name: %s", name));
                cursor = sqLiteDatabase.query(ITEM_TABLE_NAME, ITEM_ALL_COLUMNS,
                        ITEM_TABLE_COLUMN_NAME + "=?",
                        new String[] { name }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        item = new Item(cursor.getLong(0), cursor.getString(1));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return item;
    }

    @Override
    public ItemType findItemTypeByType(String type) {
        Cursor cursor = null;
        ItemType itemType = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select item type by type: %s", type));
                cursor = sqLiteDatabase.query(ITEM_TYPE_TABLE_NAME, ITEM_TYPE_ALL_COLUMNS,
                        ITEM_TYPE_TABLE_COLUMN_NAME + "=?",
                        new String[] { type }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        itemType = new ItemType(cursor.getLong(0), cursor.getString(1));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return itemType;
    }

    @Override
    public List<QuantityType> listAllQuantityTypesByRegex(String regex) {
        final List<QuantityType> quantityTypes = new ArrayList<QuantityType>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                if (regex == null || regex.isEmpty()) {
                    regex = "'%'";
                } else {
                    regex = String.format("'%s%s'", regex, "%");
                }
                final String query = "SELECT " + QUANTITY_TYPE_TABLE_COLUMN_ID + ","
                        + QUANTITY_TYPE_TABLE_COLUMN_NAME +
                        " FROM " + QUANTITY_TYPE_TABLE_NAME +
                        " WHERE lower("+QUANTITY_TYPE_TABLE_COLUMN_NAME+") like lower("+regex+")";
                Log.d(LOG_CLASS, String.format("List all quantity types by regex query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final QuantityType type = new QuantityType(cursor.getLong(0), cursor.getString(1));
                        Log.d(LOG_CLASS, String.format("Listing quantity type, %s", type.toString()));
                        quantityTypes.add(type);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return quantityTypes;
    }

    @Override
    public List<ItemType> listAllItemTypesByRegex(String regex) {
        final List<ItemType> itemTypes = new ArrayList<ItemType>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                if (regex == null || regex.isEmpty()) {
                    regex = "'%'";
                } else {
                    regex = String.format("'%s%s'", regex, "%");
                }
                final String query = "SELECT " + ITEM_TYPE_TABLE_COLUMN_ID + ","
                                               + ITEM_TYPE_TABLE_COLUMN_NAME +
                                      " FROM " + ITEM_TYPE_TABLE_NAME +
                                     " WHERE lower("+ITEM_TABLE_COLUMN_NAME+") like lower("+regex+")";
                Log.d(LOG_CLASS, String.format("List all item types by regex query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final ItemType type = new ItemType(cursor.getLong(0), cursor.getString(1));
                        Log.d(LOG_CLASS, String.format("Listing item type, %s", type.toString()));
                        itemTypes.add(type);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return itemTypes;
    }

    @Override
    public QuantityType findQuantityTypeByType(final String type) {
        Cursor cursor = null;
        QuantityType quantityType = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select quantity type by type: %s", type));
                cursor = sqLiteDatabase.query(QUANTITY_TYPE_TABLE_NAME, QUANTITY_TYPE_ALL_COLUMNS,
                        QUANTITY_TYPE_TABLE_COLUMN_NAME + "=?",
                        new String[] { type }, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        quantityType = new QuantityType(cursor.getLong(0), cursor.getString(1));
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return quantityType;
    }

    @Override
    public GroceryItem findGroceryItemById(final long groceryItemId) {
        GroceryItem groceryItem = null;
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("List all grocery item by id: %d", groceryItemId));

                final String query = "SELECT "   + ITEM_TABLE_NAME + "."
                        + ITEM_TABLE_COLUMN_ID + ", "+
                        ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_NAME + ", " +
                        GROCERY_ITEM_TABLE_NAME + "." +
                        GROCERY_ITEM_TABLE_COLUMN_ID + ", "+
                        GROCERY_ITEM_TABLE_NAME + "." +
                        GROCERY_ITEM_TABLE_COLUMN_QUANTITY + ", "+
                        QUANTITY_TYPE_TABLE_NAME + "." +
                        QUANTITY_TYPE_TABLE_COLUMN_NAME + ", "+
                        GROCERY_ITEM_TABLE_NAME + "." +
                        GROCERY_ITEM_TABLE_COLUMN_CHECKOFF + ", " +
                        ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_NAME +
                        " FROM "   + GROCERY_ITEM_TABLE_NAME +
                        " LEFT OUTER JOIN " + ITEM_TABLE_NAME + " ON " +
                        ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_ID + "=" +
                        GROCERY_ITEM_TABLE_NAME + "." + GROCERY_ITEM_TABLE_COLUMN_ITEMID +
                        " LEFT OUTER JOIN " + ITEM_TYPE_TABLE_NAME + " ON " +
                        ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_ID + "=" +
                        ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_ITEMTYPE_ID +
                        " LEFT OUTER JOIN " + QUANTITY_TYPE_TABLE_NAME +  " ON " +
                        QUANTITY_TYPE_TABLE_NAME + "." + QUANTITY_TYPE_TABLE_COLUMN_ID + "=" +
                        GROCERY_ITEM_TABLE_NAME + "." + GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID +
                        " WHERE "  + GROCERY_ITEM_TABLE_NAME + "."
                        + GROCERY_ITEM_TABLE_COLUMN_ID + "=?" +
                        " ORDER BY " + ITEM_TYPE_TABLE_NAME + "." + ITEM_TYPE_TABLE_COLUMN_NAME + ", "
                        + ITEM_TABLE_NAME + "." + ITEM_TABLE_COLUMN_NAME;

                Log.d(LOG_CLASS, String.format("Query: %s", query));
                cursor = sqLiteDatabase.rawQuery(query,
                           new String[]{String.valueOf(groceryItemId)});

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        groceryItem = new GroceryItem(cursor.getLong(2));
                        Item item = new Item(cursor.getLong(0), cursor.getString(1));
                        groceryItem.setItem(item);
                        groceryItem.setQuantity(cursor.getFloat(3));
                        groceryItem.setQuantityType(new QuantityType(cursor.getString(4)));
                        groceryItem.setCheckoff(cursor.getInt(5) == 1 ? true : false);
                        item.setItemType(new ItemType(cursor.getString(6)));
                        Log.d(LOG_CLASS, groceryItem.toString());
                        cursor.moveToNext();
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(LOG_CLASS, String.format("Caught exception: %s", e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                close();
            }
        }
        return groceryItem;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
