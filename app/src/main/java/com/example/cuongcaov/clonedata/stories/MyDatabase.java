package com.example.cuongcaov.clonedata.stories;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * MyDatabase.
 *
 * @author CuongCV
 */

public class MyDatabase extends Activity {

    private SQLiteDatabase mDb;
    private StoryOpenHelper mOpenHelper;
    private Context mContext;

    public MyDatabase(Context context) {
        this.mContext = context;
    }

    public MyDatabase open() throws SQLException {
        mOpenHelper = new StoryOpenHelper(mContext);
        mDb = mOpenHelper.getWritableDatabase();
        return this;
    }

    public void insertStory(String link) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoryOpenHelper.COLUMN_LINK, link);
        mDb.insert(StoryOpenHelper.TABLE_NAME, null, contentValues);
    }

    public void updateStory(String link){
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoryOpenHelper.COLUMN_UP, 1);
        mDb.update(StoryOpenHelper.TABLE_NAME, contentValues, StoryOpenHelper.COLUMN_LINK + " = " + link, null);
    }

    public void insertChapter(String link, long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoryOpenHelper.COLUMN_LINK, link);
        contentValues.put(StoryOpenHelper.COLUMN_UP, 1);
        mDb.insert(StoryOpenHelper.TABLE_NAME, null, contentValues);
    }

    public Boolean isExist(String link) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + StoryOpenHelper.TABLE_NAME + " WHERE " + StoryOpenHelper.COLUMN_LINK + " = '" + link + "'", null);
        Boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public List<String> getAllData() {
        List<String> result = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + StoryOpenHelper.TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public void close() {
        mOpenHelper.close();
    }

    class StoryOpenHelper extends SQLiteOpenHelper {

        public static final String DB_NAME = "DbStories";
        public static final String TABLE_NAME = "Stories";
        public static final String COLUMN_UP = "up";
        public static final String COLUMN_LINK = "link";

        public StoryOpenHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "( " + COLUMN_LINK + " TEXT PRIMARY KEY,"
                    + COLUMN_UP + " INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
