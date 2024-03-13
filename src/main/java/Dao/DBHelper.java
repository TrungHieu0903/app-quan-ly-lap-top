package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import model.Laptop;
import model.NhaCungCap;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quanlylaptop.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME1 = "nhacungcap";
    private static final String COLUMN_idNcc = "id";
    private static final String COLUMN_tenNcc = "ten";
    private static final String TABLE_NAME2 = "laptop";
    private static final String COLUMN_idLaptop = "id";
    private static final String COLUMN_tenLaptop = "ten";
    private static final String COLUMN_idNhaCungCap = "id_nhacungcap";
    private static final String COLUMN_image = "image";
    private static final String COLUMN_price = "price";
    private static final String COLUMN_quantity = "quantity";
    private static final String COLUMN_mota = "mota";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Laptop> getAllLaptop(){
        List<Laptop> laptops = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idLaptop,
                DBHelper.COLUMN_tenLaptop,
                DBHelper.COLUMN_idNhaCungCap,
                DBHelper.COLUMN_image,
                DBHelper.COLUMN_price,
                DBHelper.COLUMN_quantity,
                DBHelper.COLUMN_mota
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME2,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            Laptop laptop=new Laptop(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idLaptop)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenLaptop)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNhaCungCap)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_image)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_price)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_quantity)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_mota))
            );
            laptops.add(laptop);
        }
        cursor.close();
        return laptops;
    }

    public List<NhaCungCap> getAllNhaCungCap(){
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idNcc,
                DBHelper.COLUMN_tenNcc
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME1,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){

            NhaCungCap nhaCungCap=new NhaCungCap(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNcc)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenNcc))
            );
            nhaCungCaps.add(nhaCungCap);
        }
        cursor.close();
        return nhaCungCaps;
    }
    public void addNcc(NhaCungCap nhaCungCap) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenLaptop, nhaCungCap.getTen());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME1, null, values);
    }
    public void updateNcc(NhaCungCap nhaCungCap){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNcc, nhaCungCap.getTen());
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={nhaCungCap.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME1,values,selection,selectionArgs
        );
    }
    public void deleteNcc(int ma){
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME1,selection,selectionArgs
        );
    }
    public void addLaptop(Laptop laptop) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenLaptop, laptop.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, laptop.getIdncc());
        values.put(DBHelper.COLUMN_image, laptop.getImage());
        values.put(DBHelper.COLUMN_price, laptop.getPrice());
        values.put(DBHelper.COLUMN_quantity, laptop.getQuantity());
        values.put(DBHelper.COLUMN_mota, laptop.getMota());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME2, null, values);
    }
    public void deleteLaptop(int ma){
        String selection = DBHelper.COLUMN_idLaptop+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME2,selection,selectionArgs
        );
    }
    public void updateLaptop(Laptop laptop){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenLaptop, laptop.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, laptop.getIdncc());
        values.put(DBHelper.COLUMN_image, laptop.getImage());
        values.put(DBHelper.COLUMN_price, laptop.getPrice());
        values.put(DBHelper.COLUMN_quantity, laptop.getQuantity());
        values.put(DBHelper.COLUMN_mota, laptop.getMota());
        String selection = DBHelper.COLUMN_idLaptop+" = ?";
        String[] selectionArgs={laptop.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME2,values,selection,selectionArgs
        );
    }
    public boolean hasLaptops(int idNcc) {
        String[] projection = {COLUMN_idLaptop};
        String selection = COLUMN_idNhaCungCap + " = ?";
        String[] selectionArgs = {String.valueOf(idNcc)};

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME2, projection, selection, selectionArgs, null, null, null
        );
        boolean hasLaptops = cursor.getCount() > 0;
        cursor.close();
        System.out.println( "Has laptops: " + hasLaptops);
        return hasLaptops;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
