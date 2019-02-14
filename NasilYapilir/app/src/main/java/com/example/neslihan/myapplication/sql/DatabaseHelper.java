package com.example.neslihan.myapplication.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.neslihan.myapplication.model.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_PATH = "/data/data/com.example.neslihan.myapplication/databases/";
    private static final String DB_NAME = "KendiIsımNew";
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;



    private static final String TABLE_USER="Kullanıcılar";
    private static final String COLUMN_USER_ID="KullaniciId";
    private static final String COLUMN_USER_NAME="Ad";
    private static final String COLUMN_EMAIL="Email";
    private static final String COLUMN_USER_PASSWORD="Şifre";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            /*
             * Veritabanı daha önce mevcut o yüzden herhangi bir işlem yapmaya
             * gerek yok.
             */
        } else {
            /*
             * Veritabanı daha önce hiç oluşturulmamış o yüzden veritabanını
             * kopyala
             */
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {

                throw new Error("Veritabanı kopyalanamadı");

            }
        }

    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public SQLiteDatabase getDatabase(){
        return sqLiteDatabase;
    }
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    /*
     * Veritabanını işimiz bittiğinde kapatmak için kullanacağımız metod
     */
    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }
    public  void  addUser(User user)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_EMAIL,user.getEmail());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());
        db.insert(TABLE_USER,null,values);
        db.close();
    }

}
