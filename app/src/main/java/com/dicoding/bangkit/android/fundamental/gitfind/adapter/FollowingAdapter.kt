package com.dicoding.bangkit.android.fundamental.gitfind.adapter

import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
var followingFilterList = ArrayList<Github>()
class FollowingAdapter(private val list: ArrayList<Github>) : RecyclerView.Adapter<FollowingAdapter.FollowerViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(github: Github)
    }
    init {
        followingFilterList = list
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FollowerViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var tvuser : TextView = itemView.findViewById(R.id.tv_userlist)
        var cvgambar : ImageView = itemView.findViewById(R.id.cv_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_github,parent,false)
        val sch = FollowerViewHolder(view)
        mcontext = parent.context
        return sch
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {

        val githubb = list[position]
        holder.tvuser.text = githubb.username
        Glide.with(holder.itemView.context)
                .load(githubb.photoo).apply(RequestOptions().override(75,75))
                .into(holder.cvgambar)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}