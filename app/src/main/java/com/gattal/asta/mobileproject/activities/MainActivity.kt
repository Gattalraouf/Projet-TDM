package com.gattal.asta.mobileproject.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gattal.asta.mobileproject.NotificationUtils
import com.gattal.asta.mobileproject.R
import com.gattal.asta.mobileproject.adapters.RecyclerViewAdapter
import com.gattal.asta.mobileproject.data.FeedAPI
import com.gattal.asta.mobileproject.modeldata.AdEntity
import kotlinx.android.synthetic.main.activity_main.*
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import me.toptas.rssconverter.RssItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var products: MutableList<AdEntity>
    private lateinit var productsCopy: MutableList<AdEntity>
    private lateinit var imgs: MutableList<String>
    private lateinit var searchView: SearchView
    var ListAds: MutableList<AdEntity> = ArrayList()
    lateinit var ListItems: MutableList<RssItem>

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000
    private var mNotified = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = recyclerViewA

        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
        }

    }

    override fun onStart() {
        super.onStart()

        products = ArrayList()
        productsCopy = ArrayList()

        imgs = ArrayList()

        ListItems = getRealAds()


        recyclerViewAdapter = RecyclerViewAdapter(products)
        recyclerViewAdapter.setOnItemClickListener(this)
        recyclerView.adapter = recyclerViewAdapter

        val mLayoutManagerForItems = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        recyclerView.layoutManager = mLayoutManagerForItems
        recyclerView.itemAnimator = DefaultItemAnimator()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                recyclerViewAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                recyclerViewAdapter.filter.filter(query)
                return false
            }
        })

        val item = menu.findItem(R.id.sort)
        val spinner = item.actionView as Spinner

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_sort, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                sortData(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        return true
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }


    fun sortData(i: Int) {
        when (i) {
            0 -> {
                recyclerViewAdapter = RecyclerViewAdapter(productsCopy)
                recyclerViewAdapter.setOnItemClickListener(this)
                recyclerView.adapter = recyclerViewAdapter

            }
            1 -> {
                products.sortWith(Comparator { lhs, rhs ->
                    when {
                        lhs.category > rhs.category -> -1
                        lhs.category == rhs.category -> 0
                        else -> 1
                    }
                })
                recyclerViewAdapter = RecyclerViewAdapter(products)
                recyclerViewAdapter.setOnItemClickListener(this)
                recyclerView.adapter = recyclerViewAdapter
            }
            2 -> {
                products.sortWith(Comparator { lhs, rhs ->
                    when {
                        lhs.ownerName > rhs.ownerName -> -1
                        lhs.ownerName == rhs.ownerName -> 0
                        else -> 1
                    }
                })
                recyclerViewAdapter = RecyclerViewAdapter(products)
                recyclerViewAdapter.setOnItemClickListener(this)
                recyclerView.adapter = recyclerViewAdapter
            }
            3 -> {
                products.sortWith(Comparator { lhs, rhs ->
                    when {
                        lhs.wilaya > rhs.wilaya -> -1
                        lhs.wilaya == rhs.wilaya -> 0
                        else -> 1
                    }
                })
                recyclerViewAdapter = RecyclerViewAdapter(products)
                recyclerViewAdapter.setOnItemClickListener(this)
                recyclerView.adapter = recyclerViewAdapter
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.sort -> {

                true
            }
            R.id.search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onItemClick(view: View, obj: AdEntity, position: Int) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("annonce", obj)
        startActivity(intent)
    }


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


    // Get RSS FeedS Methods

    fun getAds(baseUrl: String, url: String): MutableList<RssItem> {
        val ListAds = mutableListOf<RssItem>()
        getRssFeed(baseUrl, url, ListAds)
        return ListAds

    }

    private fun getRssFeed(
        baseUrl: String,
        url: String,
        output: MutableList<RssItem>
    ): List<RssItem> {
        var Data = emptyList<RssItem>()

        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(RssConverterFactory.create())
            .build()
        var service = retrofit.create(FeedAPI::class.java)
        val response = service.getRss(url)
        response.enqueue(object : Callback<RssFeed> {
            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                if (response.isSuccessful()) {
                    Data = response.body()!!.items!!
                    output.addAll(Data)
                    ListItems = Data as MutableList

                    for (item: RssItem in ListItems) {
                        Log.d("data", item.toString())

                        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_01_0000_max_656x437.jpg")
                        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_02_0000_max_656x437.jpg")
                        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_07_0000_max_656x437.jpg")
                        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_08_0000_max_656x437.jpg")

                        val ad = AdEntity()

                        ad.title = item.title!!
                        ad.descript = item.description!!
                        ad.link = item.link!!
                        ad.date = item.publishDate!!
                        if (item.image != null)
                            if (item.title!!.contains("Wilaya d'")) {
                                ad.wilaya = item.title!!.substringAfter("Wilaya d'")
                            } else {
                                ad.wilaya = item.title!!.substringAfter("Wilaya de")
                            }

                        ListAds.add(ad)

                    }

                    recyclerView.adapter = RecyclerViewAdapter(ListAds)
                    (recyclerView.adapter as RecyclerViewAdapter).setOnItemClickListener(this@MainActivity)

                    Log.d("data ----------------", products.toString())
                }
            }

            override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                Log.i("error", t.message)
            }
        })

        return Data
    }


    fun getRealAds(): MutableList<RssItem> {
        val list = getAds("https://www.algerimmo.com/rss/", "?category=&type=0&location=")

        return list
    }

}
