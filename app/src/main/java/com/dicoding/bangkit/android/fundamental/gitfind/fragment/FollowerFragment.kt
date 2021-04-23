package com.dicoding.bangkit.android.fundamental.gitfind.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bangkit.android.fundamental.gitfind.adapter.FollowerAdapter
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.adapter.followersFilterList
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class FollowerFragment : Fragment() {

    companion object {

        const val EXTRA_DATA = "extra_data"
        private val TAG = FollowerFragment::class.java.simpleName
    }

    private lateinit var  rvcarfollower : RecyclerView
    private var lisitFollower : ArrayList<Github> = arrayListOf()
    private lateinit var progressBarFollower : ProgressBar
    private lateinit var followerAdapter : FollowerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvcarfollower = view.findViewById(R.id.recycleViewFollowers)
        progressBarFollower = view.findViewById(R.id.progressBarFollowers)
        followerAdapter = FollowerAdapter(lisitFollower)
        lisitFollower.clear()
        val datafollower = activity!!.intent.getParcelableExtra(EXTRA_DATA) as? Github
        getUserFollowers(datafollower?.username.toString())


    }

    private fun getUserFollowers(id: String) {
        progressBarFollower.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")
        val url = "https://api.github.com/users/${id}/followers"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                progressBarFollower.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getUserDetailFollower(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                progressBarFollower.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                        .show()
            }

        })
    }

    private fun getUserDetailFollower(id: String) {
        progressBarFollower.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")
        val url = "https://api.github.com/users/${id}"
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                progressBarFollower.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")
                    lisitFollower.add(
                            Github(
                                    username,
                                    name,
                                    avatar,
                                    company,
                                    location,
                                    repository,
                                    followers,
                                    following
                            )
                    )
                    showRecyclerListFollower()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                progressBarFollower.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                        .show()
            }

        })
    }

    private fun showRecyclerListFollower() {
        rvcarfollower.layoutManager = LinearLayoutManager(activity)
        val followeradapterdata = FollowerAdapter(followersFilterList)
        rvcarfollower.adapter = followerAdapter

        followeradapterdata.setOnItemClickCallback(object : FollowerAdapter.OnItemClickCallback{
            override fun onItemClicked(github: Github) {

            }

        })
    }


}