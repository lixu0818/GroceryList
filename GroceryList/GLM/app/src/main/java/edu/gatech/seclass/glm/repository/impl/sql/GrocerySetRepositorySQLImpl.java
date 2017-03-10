package edu.gatech.seclass.glm.repository.impl.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.domain.GroceryItem;
import edu.gatech.seclass.glm.domain.GrocerySet;
import edu.gatech.seclass.glm.repository.GrocerySetRepository;

/**
 * SQL data access for {@link edu.gatech.seclass.glm.repository.GrocerySetRepository}
 *
 * @author Team 02
 * @since 10/09/2016
 * @version 1.0
 */
public final class GrocerySetRepositorySQLImpl extends GrocerySQLiteOpenHelper implements GrocerySetRepository   {

    private final String LOG_CLASS = getClass().getName();

    public GrocerySetRepositorySQLImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public List<GrocerySet> listAllGrocerySets() {
        final List<GrocerySet> grocerySets = new ArrayList<GrocerySet>();
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select all grocery sets"));
                cursor = sqLiteDatabase.query(SET_TABLE_NAME, SET_ALL_COLUMNS, null,
                                        null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        final GrocerySet grocerySet = new GrocerySet(cursor.getLong(0), cursor.getString(1));
                        grocerySets.add(grocerySet);
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
        return grocerySets;
    }

    @Override
    public boolean updateGrocerySet(final GrocerySet set){return false;}

    @Override
    public boolean renameGrocerySet(GrocerySet grocerySet) {
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("rename grocery, %d", grocerySet.getId()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(SET_TABLE_COLUMN_NAME, grocerySet.getName());
                int count = sqLiteDatabase.update(SET_TABLE_NAME, initialValues,
                        SET_TABLE_COLUMN_ID+"=?",
                        new String[] {  Long.toString(grocerySet.getId())});
                if (count == 1) {
                    Log.d(LOG_CLASS, String.format("rename grocery set, %d", grocerySet.getId()));
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
    public long addUpdateGrocerySet(final GrocerySet set) {
        long id = -1;
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Add new grocery set for %s", set.getName()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(SET_TABLE_COLUMN_NAME, set.getName());
                if (set.getId() > 0) {
                    id = sqLiteDatabase.update(SET_TABLE_NAME, initialValues,
                            SET_TABLE_COLUMN_ID+"=?", new String[] { String.valueOf(set.getId()) });
                } else {
                    id = sqLiteDatabase.insertOrThrow(SET_TABLE_NAME, null, initialValues);
                }
                if (id > -1) {
                    Log.d(LOG_CLASS, String.format("Inserted/Updated value is: %d", id));
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
    public boolean removeGrocerySet(long id) {
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Remove grocery set: %d",  id));
                sqLiteDatabase.beginTransaction();
                int count = sqLiteDatabase.delete(SET_TABLE_NAME,
                        SET_TABLE_COLUMN_ID + " = ?", new String[] { Long.toString(id) });
                if (count == 1) {
                    Log.d(LOG_CLASS, String.format("Removed grocery set with id: %d", id));
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
    public long addUpdateGroceryItem(final GroceryItem groceryItem) {
        long id = -1;
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Add/update new grocery item for %s",
                        groceryItem.getItem().getName()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(GROCERY_ITEM_TABLE_COLUMN_CHECKOFF, groceryItem.isCheckoff());
                initialValues.put(GROCERY_ITEM_TABLE_COLUMN_GSETID,
                        groceryItem.getGrocerySet().getId());
                initialValues.put(GROCERY_ITEM_TABLE_COLUMN_ITEMID,
                        groceryItem.getItem().getId());
                initialValues.put(GROCERY_ITEM_TABLE_COLUMN_QUANTITY,
                        groceryItem.getQuantity());
                if (groceryItem.getQuantityType() != null) {
                    initialValues.put(GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID,
                            groceryItem.getQuantityType().getId());
                }
                if (groceryItem.getId() <= 0) {
                    id = sqLiteDatabase.insertOrThrow(GROCERY_ITEM_TABLE_NAME, null, initialValues);
                } else {
                    id = sqLiteDatabase.update(GROCERY_ITEM_TABLE_NAME, initialValues,
                            GROCERY_ITEM_TABLE_COLUMN_ID +" = ?",
                            new String[] { String.valueOf(groceryItem.getId())});
                }
                if (id > -1) {
                    Log.d(LOG_CLASS, String.format("Inserted/Updated id for grocery item is: %d", id));
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
    public boolean removeGroceryItem(GroceryItem groceryItem) {
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Remove grocery item, %d", groceryItem.getId()));
                sqLiteDatabase.beginTransaction();
                int count = sqLiteDatabase.delete(GROCERY_ITEM_TABLE_NAME,
                        GROCERY_ITEM_TABLE_COLUMN_ID+"=?",
                        new String[] { String.valueOf(groceryItem.getId())});
                if (count == 1) {
                    Log.d(LOG_CLASS, String.format("Deleted grocery item, %d", groceryItem.getId()));
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
    public boolean checkoffGroceryItem(final GroceryItem groceryItem) {
        try {
            openForWrite();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Checkoff grocery item, %d to %b", groceryItem.getId(), groceryItem.isCheckoff()));
                sqLiteDatabase.beginTransaction();
                final ContentValues initialValues = new ContentValues();
                initialValues.put(GROCERY_ITEM_TABLE_COLUMN_CHECKOFF, groceryItem.isCheckoff());
                int count = sqLiteDatabase.update(GROCERY_ITEM_TABLE_NAME, initialValues,
                        GROCERY_ITEM_TABLE_COLUMN_ID+"=?",
                        new String[] { String.valueOf(groceryItem.getId())});
                if (count == 1) {
                    Log.d(LOG_CLASS, String.format("Checked grocery item, %d", groceryItem.getId()));
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
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_CLASS, "Creating tables");
        Log.d(LOG_CLASS, String.format("Creating quantity_type: %s", QUANTITY_TYPE_TABLE_CREATE));
        db.execSQL(QUANTITY_TYPE_TABLE_CREATE);
        Log.d(LOG_CLASS, String.format("Creating grocery_set: %s", SET_TABLE_CREATE));
        db.execSQL(SET_TABLE_CREATE);
        Log.d(LOG_CLASS, String.format("Creating item_type: %s", ITEM_TYPE_TABLE_CREATE));
        db.execSQL(ITEM_TYPE_TABLE_CREATE);
        Log.d(LOG_CLASS, String.format("Creating item: %s", ITEM_TABLE_CREATE));
        db.execSQL(ITEM_TABLE_CREATE);
        Log.d(LOG_CLASS, String.format("Creating grocery_item: %s", GROCERY_ITEM_TABLE_CREATE));
        db.execSQL(GROCERY_ITEM_TABLE_CREATE);
    }

    @Override
    public GrocerySet findGrocerySetById(long id) {
        GrocerySet grocerySet = null;
        Cursor cursor = null;
        try {
            openForRead();
            if (sqLiteDatabase != null) {
                Log.d(LOG_CLASS, String.format("Select grocery set by id: %d", id));
                cursor = sqLiteDatabase.query(SET_TABLE_NAME, SET_ALL_COLUMNS,
                        SET_TABLE_COLUMN_ID+"=?", new String[] { String.valueOf(id)},
                        null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        grocerySet = new GrocerySet(cursor.getLong(0), cursor.getString(1));
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
        return grocerySet;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_CLASS, "Upgrading tables");
        db.execSQL("drop table if exists " + GROCERY_ITEM_TABLE_NAME);
        db.execSQL("drop table if exists " + SET_TABLE_NAME);
        db.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        db.execSQL("drop table if exists " + ITEM_TYPE_TABLE_NAME);
        db.execSQL("drop table if exists " + QUANTITY_TYPE_TABLE_NAME);
        onCreate(db);
    }
}
