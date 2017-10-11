package com.example.karim.developers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TooManyListenersException;

/**
 * Created by elanaggar on 20/09/17.
 */

public class SQLiteController {

    public static final String TABLE_NAME = "model";
    public static final String COLUMN_NAME = "name";
    public static final int DATA_BASE_VERSION = 1;
    public static final String downloaded = "downloaded";
    public static final String DATA_BASE_NAME = "myDataBase";
    public static final String owner_uid="owner";
    private String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME+ " TEXT PRIMARY KEY,  "+owner_uid+" TEXT , "
                    +downloaded+" TEXT );"
                    ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private SQLiteDatabase mDataBase;
    Context context;

    public void open(Context context) {
        SQLHelper sqlHelper = new SQLHelper(context);
        mDataBase = sqlHelper.getWritableDatabase();
        this.context=context;
    }

    public void insert(aplication model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, model.getPackageName());
        contentValues.put(owner_uid,model.getUid());
        contentValues.put(downloaded,"0");
        mDataBase.insert(TABLE_NAME, null, contentValues);
    }
    public void update2(objectForsql sql){
        ContentValues cv = new ContentValues();
        cv.put(downloaded,"1");

       mDataBase.update(TABLE_NAME, cv, COLUMN_NAME+"="+sql.getPackage(), null);
    }
    public void update(objectForsql title) {
        ContentValues values = new ContentValues();
        values.put(downloaded, "1");

// Which row to update, based on the title
        String selection = COLUMN_NAME+ " LIKE ?";
        String[] selectionArgs = {title.getPackage()};

        int count = mDataBase.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);


    }
    public ArrayList<objectForsql> getAllData() {

        String[] projection = {
                COLUMN_NAME,owner_uid,downloaded
        };


        Cursor cursor = mDataBase.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        ArrayList<objectForsql> models = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString
                    (cursor.getColumnIndex(COLUMN_NAME));
            String uid = cursor.getString
                    (cursor.getColumnIndex(owner_uid));
            String download = cursor.getString(
                    (cursor.getColumnIndex(downloaded)));
            Log.e("sdfs",title);
            models.add(new objectForsql(title,uid,download));
        }


        return models;
    }

    public void close() {
        mDataBase.close();
    }

    public class SQLHelper extends SQLiteOpenHelper {


        public SQLHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}