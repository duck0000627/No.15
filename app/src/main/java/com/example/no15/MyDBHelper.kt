package com.example.no15

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context,
                 name: String = database,
                 factory: SQLiteDatabase.CursorFactory? = null,
                 version: Int = v):SQLiteOpenHelper(context, name, factory, version) {
                     companion object{
                         private const val database = "FarmWorkDatabase"
                         private const val v = 1
                     }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE FarmWorkDB(crop text PRIMARY KEY,date text NOT NULL,code text NOT NULL,number text NOT NULL,work text NOT NULL,tips text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS FarmWorkDatabase")
        onCreate(db)
    }
}