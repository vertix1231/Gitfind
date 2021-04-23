package com.dicoding.bangkit.android.fundamental.gitfind.adapter

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.activity.DetailUserActivity
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
var githubuserFilterList = ArrayList<Github>()
lateinit var mcontext: Context
class GithubAdapter(private val listgithub : ArrayList<Github>) : RecyclerView.Adapter<GithubAdapter.ListViewHolder>() {
    init {
        githubuserFilterList = listgithub
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_github,parent,false)
        return ListViewHolder(view)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val githubb = listgithub[position]
        holder.tvusername.text = githubb.username
        Glide.with(holder.itemView.context)
                .load(githubb.photoo).apply(RequestOptions().override(75,75))
                .into(holder.cvgambar)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu memilih " + listgithub[position].username, Toast.LENGTH_SHORT).show()
        }

        val dataUser = Github(
                githubb.username,
                githubb.name,
                githubb.photoo,
                githubb.location,
                githubb.repository,
                githubb.company,
                githubb.followers,
                githubb.following,

        )

        val mContextt = holder.itemView.context
        holder.itemView.setOnClickListener {
            val movedetailuseractivity = Intent(mContextt,DetailUserActivity::class.java)
            movedetailuseractivity.putExtra(DetailUserActivity.EXTRA_DATA,dataUser)
            movedetailuseractivity.putExtra(DetailUserActivity.EXTRA_FAV, dataUser)
            mContextt.startActivity(movedetailuseractivity)
        }
    }

    override fun getItemCount(): Int {
        return listgithub.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvusername : TextView = itemView.findViewById(R.id.tv_userlist)
        var cvgambar : ImageView = itemView.findViewById(R.id.cv_user)

    }
}