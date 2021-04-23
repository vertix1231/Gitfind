package com.dicoding.bangkit.android.fundamental.gitfind.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.adapter.SectionsPagerAdapter
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.AVATAR
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.COMPANY
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.FAVORITE
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.LOCATION
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.NAME
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.REPOSITORY
import com.dicoding.bangkit.android.fundamental.gitfind.databases.DatabaseContract.FavColumns.Companion.USERNAME
import com.dicoding.bangkit.android.fundamental.gitfind.databases.FavHelper
import com.dicoding.bangkit.android.fundamental.gitfind.pojo.Favorite
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    private lateinit var tvusername : TextView
    private lateinit var tvname : TextView
    private lateinit var tvrepo : TextView
    private lateinit var tvcompany : TextView
    private lateinit var tvlocation : TextView
    private lateinit var cvfoto : CircleImageView
    private var isFavorite = false
    private lateinit var gitHelper: FavHelper
    private var favorites: Favorite? = null
    private lateinit var imageAvatar: String
    private lateinit var btnimg_favorite : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        tvusername = findViewById(R.id.tvusernamedetail)
        tvname = findViewById(R.id.tvnamedetail)
        tvrepo = findViewById(R.id.tvrepodetail)
        tvcompany = findViewById(R.id.tvcompanydetail)
        tvlocation = findViewById(R.id.tvlocationdetail)
        cvfoto = findViewById(R.id.cv_user_detail)
        btnimg_favorite = findViewById(R.id.btnimg_favorite)

        gitHelper = FavHelper.getInstance(applicationContext)
        gitHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null){
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.favorite_red_hearth
            btnimg_favorite.setImageResource(checked)
        }else{
            setData()
        }
        setData()
        setTabDetail()
    }

    private fun setTabDetail(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun setData() {


        val receivepaket = intent.getParcelableExtra<Github>(EXTRA_DATA) as Github

        tvusername.text = receivepaket.username
        tvname.text = receivepaket.name
        tvcompany.text = receivepaket.company
        tvlocation.text = receivepaket.location
        tvrepo.text = receivepaket.repository
        Glide.with(this)
                .load(receivepaket.photoo)
                .apply(RequestOptions().override(75,75))
                .into(cvfoto)
        imageAvatar = receivepaket.photoo.toString()

    }


    private fun setDataObject() {

        val favoriteUser = intent.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite

        tvusername.text = favoriteUser.username
        tvname.text = favoriteUser.name
        tvcompany.text = favoriteUser.company
        tvlocation.text = favoriteUser.location
        tvrepo.text = favoriteUser.repository
        Glide.with(this)
            .load(favoriteUser.photoo)
            .apply(RequestOptions().override(75,75))
            .into(cvfoto)
        imageAvatar = favoriteUser.photoo.toString()
    }

    override fun onClick(view: View) {
        val checked: Int = R.drawable.favorite_red_hearth
        val unChecked: Int = R.drawable.favorite_grey_hearth
        if (view.id == R.id.btnimg_favorite) {
            if (isFavorite) {
                gitHelper.deleteById(favorites?.username.toString())
                Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show()
                btnimg_favorite.setImageResource(unChecked)
                isFavorite = false
            } else {
                val dataUsername = tvusername.text.toString()
                val dataName = tvname.text.toString()
                val dataAvatar = imageAvatar
                val datacompany = tvcompany.text.toString()
                val dataLocation = tvlocation.text.toString()
                val dataRepository = tvrepo.text.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(AVATAR, dataAvatar)
                values.put(COMPANY, datacompany)
                values.put(LOCATION, dataLocation)
                values.put(REPOSITORY, dataRepository)
                values.put(FAVORITE, dataFavorite)

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                btnimg_favorite.setImageResource(checked)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_change_notification -> {
                val mIntent = Intent(this, NotificationSettingsActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_favorite -> {
                val mIntent = Intent(this, UserFavoriteActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}