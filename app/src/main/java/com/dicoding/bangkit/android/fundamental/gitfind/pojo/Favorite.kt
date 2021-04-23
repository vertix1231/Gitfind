package com.dicoding.bangkit.android.fundamental.gitfind.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
        var username: String? = null,
        var name: String? = null,
        var photoo: String? = null,
        var company: String? = null,
        var location: String? = null,
        var repository: String? = null,
        var followers: String? = null,
        var following: String? = null,
        var isFav: String? = null,
) : Parcelable
