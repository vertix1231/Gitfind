package com.dicoding.bangkit.android.fundamental.gitfind.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.TABLE_NAME
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.USERNAME
import java.sql.SQLException
import kotlin.jvm.Throws

class FavHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper : DatabaseHelper
        private lateinit var database : SQLiteDatabase
        private var INSTANCE : FavHelper? = null




        //menginisiasi database.
        fun getInstance(context: Context) : FavHelper{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavHelper(context)
            }
        }

    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    //buka akses database
    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    //tutup akses database
    @Throws(SQLException::class)
    fun close(){
        databaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    //proses CRUD-nya
    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$USERNAME ASC")
    }

    //proses CRUD-nya berdasarkan pengambilan data unik id
    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    // menyimpan data.
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    //update data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }

    //hapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}