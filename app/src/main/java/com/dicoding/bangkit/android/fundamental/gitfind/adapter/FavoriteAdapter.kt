package com.dicoding.bangkit.android.fundamental.gitfind.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.activity.DetailUserActivity
import com.dicoding.bangkit.android.fundamental.gitfind.onclick.CustomOnItemClickListener
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Favorite
import java.util.ArrayList

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_github, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvusername : TextView = itemView.findViewById(R.id.tv_userlist)
        var cvgambar : ImageView = itemView.findViewById(R.id.cv_user)
        fun bind(fav: Favorite) {
            with(itemView) {
                Glide.with(itemView.context)
                        .load(fav.photoo)
                        .apply(RequestOptions().override(250, 250))
                        .into(cvgambar)
                tvusername.text = fav.username
                itemView.setOnClickListener(
                        CustomOnItemClickListener(
                                adapterPosition,
                                object : CustomOnItemClickListener.OnItemClickCallback {
                                    override fun onItemClicked(view: View, position: Int) {
                                        val intent = Intent(activity, DetailUserActivity::class.java)
                                        intent.putExtra(DetailUserActivity.EXTRA_POSITION, position)
                                        intent.putExtra(DetailUserActivity.EXTRA_NOTE, fav)
                                        activity.startActivity(intent)
                                    }
                                }
                        )
                )
            }
        }

    }



}