package com.dicoding.bangkit.android.fundamental.gitfind.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.adapter.FavoriteAdapter
import com.dicoding.bangkit.android.fundamental.gitfind.adapter.GithubAdapter
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.dicoding.bangkit.android.fundamental.gitfind.helper.MappingHelper
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Favorite
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Github
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var  recycleViewFav : RecyclerView
    private var list : ArrayList<Github> = arrayListOf()
    private lateinit var progressBarFav : ProgressBar
    private lateinit var adapter: FavoriteAdapter
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_favorite)

        recycleViewFav.layoutManager = LinearLayoutManager(this)
        recycleViewFav.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        recycleViewFav.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }
    // get ambil data and set masukan ke adapter dari SQLite database
    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main){
            progressBarFav.visibility = View.VISIBLE
            val deferredfav = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredfav.await()
            progressBarFav.visibility = View.INVISIBLE
            if (favData.size > 0){
                adapter.listFavorite = favData
            }else{
                adapter.listFavorite = ArrayList()
                showSnackbarMessage()
            }
        }
    }
    private fun showSnackbarMessage() {
        Toast.makeText(this, getString(R.string.empty_favorite), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }


    // run this func when open again for refresh data
    override fun onResume() {
        super.onResume()
        loadNotesAsync()
    }
}