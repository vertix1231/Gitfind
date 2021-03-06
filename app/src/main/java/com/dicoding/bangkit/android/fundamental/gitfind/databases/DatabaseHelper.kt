package com.dicoding.bangkit.android.fundamental.gitfind.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "dbfavgithubakun"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAV = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.FavColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                "${DatabaseContract.FavColumns.NAME} TEXT NOT NULL," +
                "${DatabaseContract.FavColumns.AVATAR} TEXT NOT NULL," +
                "${DatabaseContract.FavColumns.COMPANY} TEXT NOT NULL," +
                "${DatabaseContract.FavColumns.FAVORITE} TEXT NOT NULL," +
                "${DatabaseContract.FavColumns.LOCATION} TEXT NOT NULL," +
                "${DatabaseContract.FavColumns.REPOSITORY} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newversion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}