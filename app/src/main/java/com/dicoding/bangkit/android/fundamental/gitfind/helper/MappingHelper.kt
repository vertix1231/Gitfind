package com.dicoding.bangkit.android.fundamental.gitfind.helper

import android.database.Cursor
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Favorite
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.REPOSITORY))
                val favorite =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.FAVORITE))
                favoriteList.add(
                    Favorite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        favorite
                    )
                )
            }
        }
        return favoriteList
    }
}