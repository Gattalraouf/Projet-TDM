package com.gattal.asta.mobileproject.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.gattal.asta.mobileproject.R
import com.gattal.asta.mobileproject.adapters.RecyclerViewAdapter
import com.gattal.asta.mobileproject.data.Owner
import com.gattal.asta.mobileproject.data.Product
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var products: MutableList<Product>
    private lateinit var productsCopy: MutableList<Product>
    private lateinit var imgs: MutableList<String>
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = recyclerViewA

    }

    override fun onStart() {
        super.onStart()

        products = ArrayList()
        productsCopy = ArrayList()

        imgs = ArrayList()
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_01_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_02_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_07_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82406102/7730_28837932_IMG_08_0000_max_656x437.jpg")

        products.add(
            Product(
                Owner(
                    "Jane Doe",
                    "https://www.whatsappprofiledpimages.com/wp-content/uploads/2018/11/profile-yopic-download-43-300x222.gif",
                    "bla.bli@gmail.com",
                    "213698938280"
                ), imgs, "Ashley Park Road, YORK", "This large family house...", "Alger", "13/11/2018"
            )
        )

        imgs = ArrayList()
        imgs.add("https://media.rightmove.co.uk/dir/77k/76006/82194167/76006_76006_205989_IMG_01_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/77k/76006/82194167/76006_76006_205989_IMG_02_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/77k/76006/82194167/76006_76006_205989_IMG_03_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/77k/76006/82194167/76006_76006_205989_IMG_08_0000_max_656x437.jpg")

        products.add(
            Product(
                Owner(
                    "Jane Doe",
                    "https://www.whatsappprofiledpimages.com/wp-content/uploads/2018/11/profile-yopic-download-43-300x222.gif",
                    "bla.bli@gmail.com",
                    "213698938280"
                ),
                imgs,
                "28 Gillygate, York",
                "Large mature garden to the rear extending up to the historic city walls. Pedestrian side access to Gillygate.",
                "Alger",
                "13/11/2018"
            )
        )

        imgs = ArrayList()
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82374362/7730_28835018_IMG_01_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82374362/7730_28835018_IMG_06_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82374362/7730_28835018_IMG_07_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/8k/7730/82374362/7730_28835018_IMG_12_0000_max_656x437.jpg")

        products.add(
            Product(
                Owner(
                    "Jane Doe",
                    "https://www.whatsappprofiledpimages.com/wp-content/uploads/2018/11/profile-yopic-download-43-300x222.gif",
                    "bla.bli@gmail.com",
                    "213698938280"
                ),
                imgs,
                "The Residence, YORK",
                "The Penthouse is the largest apartment on the development measuring approximately 3000 sq ft.",
                "Oran",
                "13/11/2018"
            )
        )

        imgs = ArrayList()
        imgs.add("https://media.rightmove.co.uk/dir/54k/53812/81188750/53812_YOR190034_IMG_01_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/54k/53812/81188750/53812_YOR190034_IMG_03_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/54k/53812/81188750/53812_YOR190034_IMG_04_0000_max_656x437.jpg")
        imgs.add("https://media.rightmove.co.uk/dir/54k/53812/81188750/53812_YOR190034_IMG_09_0000_max_656x437.jpg")


        products.add(
            Product(
                Owner(
                    "Jane Doe",
                    "https://www.whatsappprofiledpimages.com/wp-content/uploads/2018/11/profile-yopic-download-43-300x222.gif",
                    "bla.bli@gmail.com",
                    "213698938280"
                ),
                imgs,
                "The Old Fire Station, York",
                "This exceptional re-development of one of York's most famous sites will deliver 7 exceptional apartments.",
                "Oran",
                "13/11/2018"
            )
        )

        productsCopy = products.toMutableList()


        val intent = intent
        val product: Product
        if (intent.getSerializableExtra("newProduct") != null) {
            product = intent.getSerializableExtra("newProduct") as Product
            products.add(product)
            productsCopy.add(product)
        }

        recyclerViewAdapter = RecyclerViewAdapter(products)
        recyclerViewAdapter.setOnItemClickListener(this)
        recyclerView.adapter = recyclerViewAdapter

        val mLayoutManagerForItems = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = mLayoutManagerForItems
        recyclerView.itemAnimator = DefaultItemAnimator()

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }


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
                        lhs.name > rhs.name -> -1
                        lhs.name == rhs.name -> 0
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
                        lhs.owner.name > rhs.owner.name -> -1
                        lhs.owner.name == rhs.owner.name -> 0
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
                        lhs.Wilaya > rhs.Wilaya -> -1
                        lhs.Wilaya == rhs.Wilaya -> 0
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


    override fun onItemClick(view: View, obj: Product, position: Int) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("product", obj)
        startActivity(intent)
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

}
