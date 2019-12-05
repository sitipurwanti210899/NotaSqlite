package com.gosigitgo.notasqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gosigitgo.notasqlite.model.Nota;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.gosigitgo.notasqlite.database.DatabaseContract.NotaColumn.DESKRIPSI;
import static com.gosigitgo.notasqlite.database.DatabaseContract.NotaColumn.JUDUL;
import static com.gosigitgo.notasqlite.database.DatabaseContract.NotaColumn.TANGGAL;
import static com.gosigitgo.notasqlite.database.DatabaseContract.TABLE_NOTA;

public class NotaHelper {

    private static DatabaseHelper databaseHelper;
    private static NotaHelper notaHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static String DATABASE_TABLE =TABLE_NOTA;

    public NotaHelper(Context context){ databaseHelper = new DatabaseHelper(context);}

    public void open(){ sqLiteDatabase =databaseHelper.getWritableDatabase(); }

    //untuk ambil semua data yang ada di table
    public ArrayList<Nota> getAllNota(){
        ArrayList<Nota> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC",
                null);

        cursor.moveToFirst();

        Nota nota;
        if (cursor.getCount()>0){
            do{
                nota=new Nota();
                nota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                nota.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                nota.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                nota.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));

                arrayList.add(nota);
                cursor.moveToNext();
                }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    //method insert data
    public void insertNota(Nota nota){
        ContentValues contentValues=new ContentValues();
        contentValues.put(JUDUL, nota.getJudul());
        contentValues.put(DESKRIPSI, nota.getDeskripsi());
        contentValues.put(TANGGAL, nota.getTanggal());
        sqLiteDatabase.insert(DATABASE_TABLE, null , contentValues);
    }
    public void updateNota(Nota nota){
        ContentValues contentValues=new ContentValues();
        contentValues.put(JUDUL, nota.getJudul());
        contentValues.put(DESKRIPSI, nota.getDeskripsi());
        contentValues.put(TANGGAL, nota.getTanggal());
        sqLiteDatabase.update(DATABASE_TABLE, contentValues, _ID + " = '"+ nota.getId() + "'",null);

    }
    public void deleteNota(int id){
        sqLiteDatabase.delete(DATABASE_TABLE, _ID + "='" + id + "'",null);
    }
}
