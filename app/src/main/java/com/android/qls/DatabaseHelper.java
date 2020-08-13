package com.android.qls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.qls.model.Book;
import com.android.qls.model.BookType;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "qls";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_BOOK_TYPE = "type";

    // Common column names
    private static final String KEY_ID_BOOK = "id";

    // Table toy
    private static final String KEY_NAME_BOOK = "namebook";
    private static final String KEY_TYPE_ID = "idType";
    private static final String KEY_TYPE_NAME = "typeName";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_REVIEW = "review";

    //Table type
    private static final String KEY_ID_TYPE = "id";
    private static final String KEY_NAME_TYPE = "nametype";
    private static final String KEY_DES = "describe";

    private static final String SQL_CREATE_TABLE_BOOK = "CREATE TABLE "
            + TABLE_BOOK + "("
            + KEY_ID_BOOK + " INTEGER,"
            + KEY_NAME_BOOK + " TEXT,"
            + KEY_TYPE_ID + " INTEGER,"
            + KEY_TYPE_NAME + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_IMAGE + " TEXT,"
            + KEY_REVIEW + " INTEGER"
            + ")";

    private static final String SQL_CREATE_TABLE_BOOK_TYPE = "CREATE TABLE "
            + TABLE_BOOK_TYPE + "(" + KEY_ID_TYPE + " INTEGER PRIMARY KEY,"
            + KEY_NAME_TYPE + " TEXT,"
            + KEY_DES + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BOOK);
        db.execSQL(SQL_CREATE_TABLE_BOOK_TYPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_TYPE);

        // create new tables
        onCreate(db);
    }

    public void addToyType(BookType bookType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_TYPE, bookType.getName());
        values.put(KEY_DES, bookType.getDescribe());
        db.insert(TABLE_BOOK_TYPE, null, values);
    }

    public void deleteToyType(BookType bookType) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BOOK_TYPE, KEY_ID_TYPE + " = ?", new String[]{String.valueOf(bookType.getId())});
    }

    public void updateToyType(BookType bookType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_TYPE, bookType.getName());
        values.put(KEY_DES, bookType.getDescribe());
        db.update(TABLE_BOOK_TYPE, values, KEY_ID_TYPE + " = ?", new String[]{String.valueOf(bookType.getId())});
    }

    public ArrayList<BookType> getAllToyType() {
        ArrayList<BookType> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOK_TYPE,
                null, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String describe = cursor.getString(2);
            list.add(new BookType(id, name, describe));
            cursor.moveToNext();
        }
        return list;
    }

    public void addBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_BOOK, book.getName());
        values.put(KEY_TYPE_ID, book.getIdType());
        values.put(KEY_TYPE_NAME, book.getTypeName());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_IMAGE, book.getImage());
        values.put(KEY_REVIEW, book.getReview());
        values.put(KEY_ID_BOOK, book.getId());
        db.insert(TABLE_BOOK, null, values);
    }

    public void deleteToy(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BOOK, KEY_ID_BOOK + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public void updateToy(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_BOOK, book.getName());
        values.put(KEY_TYPE_ID, book.getIdType());
        values.put(KEY_TYPE_NAME, book.getTypeName());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_IMAGE, book.getImage());
        values.put(KEY_REVIEW, book.getReview());
        db.update(TABLE_BOOK, values, KEY_ID_BOOK + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public ArrayList<Book> getAllToy() {
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOK,
                null, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int idType = cursor.getInt(2);
            String typeName = cursor.getString(3);
            String description = cursor.getString(4);
            String image = cursor.getString(5);
            int review = cursor.getInt(6);
            list.add(new Book(id, name, idType, typeName, description, image, review));
            cursor.moveToNext();
        }
        return list;
    }

    public ArrayList<Book> getToyByStartAndType(int star, String type) {
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

//        Cursor cursor = db.rawQuery("select * from " +TABLE_BOOK +" where " + KEY_TYPE_NAME + " = '" + type +"'", null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_BOOK +
                " where " + KEY_TYPE_NAME + " = '" + type + "'" +
                " and " + KEY_REVIEW + " >= '" + star + "'" , null);

        if (cursor != null) cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int idType = cursor.getInt(2);
            String typeName = cursor.getString(3);
            String description = cursor.getString(4);
            String image = cursor.getString(5);
            int review = cursor.getInt(6);
            list.add(new Book(id, name, idType, typeName, description, image, review));
        } while (cursor.moveToNext());
        return list;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}