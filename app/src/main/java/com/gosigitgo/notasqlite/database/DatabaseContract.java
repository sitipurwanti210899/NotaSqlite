package com.gosigitgo.notasqlite.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NOTA ="nota";

    public static final class  NotaColumn implements BaseColumns{

        public static String JUDUL ="judul";
        public static String DESKRIPSI ="deskripsi";
        public static String TANGGAL ="tanggal";
    }
}
