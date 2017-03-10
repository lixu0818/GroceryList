package edu.gatech.seclass.glm.repository.impl.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpen helper for managing database access for the application.
 *
 * @author Team 02
 * @since 10/12/2016
 * @version 1.0
 */
public abstract class GrocerySQLiteOpenHelper extends SQLiteOpenHelper {

    protected SQLiteDatabase sqLiteDatabase;

    protected static final String DATABASE_NAME = "groceries";
    protected static final int DATABASE_VERSION = 1;

    /* Grocery Set Table */
    protected static final String SET_TABLE_NAME = "grocery_set";
    protected static final String SET_TABLE_COLUMN_ID = "_id";
    protected static final String SET_TABLE_COLUMN_NAME = "name";
    protected static final String SET_TABLE_CREATE = "create table "
            + SET_TABLE_NAME
            + " ( "
            + SET_TABLE_COLUMN_ID + " integer primary key autoincrement, "
            + SET_TABLE_COLUMN_NAME + " text not null "
            + " );";
    protected static final String[] SET_ALL_COLUMNS = new String[] {
            SET_TABLE_COLUMN_ID, SET_TABLE_COLUMN_NAME};

    /* Quantity Type Table */
    protected static final String QUANTITY_TYPE_TABLE_NAME = "quantity_type";
    protected static final String QUANTITY_TYPE_TABLE_COLUMN_ID = "_id";
    protected static final String QUANTITY_TYPE_TABLE_COLUMN_NAME = "type";
    protected static final String QUANTITY_TYPE_TABLE_CREATE = "create table "
            + QUANTITY_TYPE_TABLE_NAME
            + " ( "
            + QUANTITY_TYPE_TABLE_COLUMN_ID + " integer primary key autoincrement, "
            + QUANTITY_TYPE_TABLE_COLUMN_NAME + " text not null "
            + " );";

    protected static final String[] QUANTITY_TYPE_ALL_COLUMNS = new String[] {
            QUANTITY_TYPE_TABLE_COLUMN_ID, QUANTITY_TYPE_TABLE_COLUMN_NAME};

    /* Item Type Table */
    protected static final String ITEM_TYPE_TABLE_NAME = "item_type";
    protected static final String ITEM_TYPE_TABLE_COLUMN_ID = "_id";
    protected static final String ITEM_TYPE_TABLE_COLUMN_NAME = "name";
    protected static final String ITEM_TYPE_TABLE_CREATE = "create table "
            + ITEM_TYPE_TABLE_NAME
            + " ( "
            + ITEM_TYPE_TABLE_COLUMN_ID + " integer primary key autoincrement, "
            + ITEM_TYPE_TABLE_COLUMN_NAME + " text not null "
            + " );";

    protected static final String[] ITEM_TYPE_ALL_COLUMNS = new String[] {
            ITEM_TYPE_TABLE_COLUMN_ID, ITEM_TYPE_TABLE_COLUMN_NAME};

    /* Item Table */
    protected static final String ITEM_TABLE_NAME = "item";
    protected static final String ITEM_TABLE_COLUMN_ID = "_id";
    protected static final String ITEM_TABLE_COLUMN_NAME = "name";
    protected static final String ITEM_TABLE_COLUMN_ITEMTYPE_ID = "item_id";
    protected static final String ITEM_TABLE_CREATE = "create table "
            + ITEM_TABLE_NAME
            + " ( "
            + ITEM_TABLE_COLUMN_ID + " integer primary key autoincrement, "
            + ITEM_TABLE_COLUMN_NAME + " text not null, "
            + ITEM_TABLE_COLUMN_ITEMTYPE_ID + " integer not null, "
            + "foreign key ( " + ITEM_TABLE_COLUMN_ITEMTYPE_ID + " ) references "
            + ITEM_TYPE_TABLE_NAME + " ( " + ITEM_TYPE_TABLE_COLUMN_ID + " ) "
            + " );";

    protected static final String[] ITEM_ALL_COLUMNS = new String[] {
            ITEM_TABLE_COLUMN_ID, ITEM_TABLE_COLUMN_NAME};

    protected static final String GROCERY_ITEM_TABLE_NAME = "grocery_item";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_ID = "_id";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_GSETID = "grocery_set_id";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_ITEMID = "item_id";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_QUANTITY = "quantity";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID = "quantity_type_id";
    protected static final String GROCERY_ITEM_TABLE_COLUMN_CHECKOFF = "checkoff";

    protected static final String[] GROCERY_ITEM_ALL_COULMNS = new String[] {
            GROCERY_ITEM_TABLE_COLUMN_ID, GROCERY_ITEM_TABLE_COLUMN_GSETID, GROCERY_ITEM_TABLE_COLUMN_ITEMID,
            GROCERY_ITEM_TABLE_COLUMN_QUANTITY, GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID, GROCERY_ITEM_TABLE_COLUMN_CHECKOFF};

    protected static final String GROCERY_ITEM_TABLE_CREATE = "create table "
            + GROCERY_ITEM_TABLE_NAME
            + " ( "
            + GROCERY_ITEM_TABLE_COLUMN_ID	+ " integer primary key autoincrement, "
            + GROCERY_ITEM_TABLE_COLUMN_QUANTITY + " real not null, "
            + GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID + " integer, "
            + GROCERY_ITEM_TABLE_COLUMN_CHECKOFF + " integer not null, "
            + GROCERY_ITEM_TABLE_COLUMN_GSETID + " integer not null, "
            + GROCERY_ITEM_TABLE_COLUMN_ITEMID + " integer not null, "
            + "foreign key ( " + GROCERY_ITEM_TABLE_COLUMN_GSETID + " ) references "
            + SET_TABLE_NAME + " ( " + SET_TABLE_COLUMN_ID + " ), "
            + "foreign key ( " + GROCERY_ITEM_TABLE_COLUMN_ITEMID + " ) references "
            + ITEM_TABLE_NAME + " ( " + ITEM_TABLE_COLUMN_ID + " ), "
            + "foreign key ( " + GROCERY_ITEM_TABLE_COLUMN_QUANTITYTYPE_ID + " ) references "
            + QUANTITY_TYPE_TABLE_NAME + " ( " + QUANTITY_TYPE_TABLE_COLUMN_ID + " ) "
            + " );";

    public GrocerySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version, null);
    }

    public void openForWrite() {
        sqLiteDatabase = getWritableDatabase();
    }

    public void openForRead() {
        sqLiteDatabase = getWritableDatabase();
    }

}
