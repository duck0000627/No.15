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
                         private const val v = 18
                     }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE FarmWorkDB(crop text NOT NULL,date text NOT NULL,code text NOT NULL,number text NOT NULL,work text NOT NULL,tips text," +
                "mucktype text,muckname text,muckcount text,muckcounttype text," +
                "wormwho text,wormname text,wormnumber text,wormuse text,wormmultiple text,wormother text,id integer PRIMARY KEY AUTOINCREMENT)")
        db.execSQL("CREATE TABLE MuckWorkDB(type text NOT NULL,muckname text NOT NULL,count text NOT NULL,counttype text NOT NULL)")
        db.execSQL("CREATE TABLE WormWorkDB(who text PRIMARY KEY,name text NOT NULL,number text NOT NULL,use text NOT NULL,multiple text NOT NULL,other text)")
        db.execSQL("CREATE TABLE OtherWorkDB(date text PRIMARY KEY,text text NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS FarmWorkDB")
        db.execSQL("DROP TABLE IF EXISTS MuckWorkDB")
        db.execSQL("DROP TABLE IF EXISTS WormWorkDB")
        db.execSQL("DROP TABLE IF EXISTS OtherWorkDB")
        onCreate(db)
    }
}