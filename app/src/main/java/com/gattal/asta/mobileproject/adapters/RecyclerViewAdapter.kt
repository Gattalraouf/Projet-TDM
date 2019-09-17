package com.gattal.asta.mobileproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gattal.asta.mobileproject.R
import com.gattal.asta.mobileproject.modeldata.AdEntity
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerViewAdapter(private val productArrayList: List<AdEntity>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private var products: List<AdEntity>? = productArrayList
    private var productsFiltered: List<AdEntity>? = productArrayList
    private var itemClickListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: AdEntity, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        Log.d("dataAdapter", productArrayList.toString())

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {


            val product = this.productsFiltered!![position]

            viewHolder.productName.text = product.title
            viewHolder.productowner.text = product.ownerName
            viewHolder.wilaya.text = product.wilaya

            Glide.with(viewHolder.itemImageView.context)
                .load(product.imgs[0])
                .into(viewHolder.itemImageView)

            if (itemClickListener != null) {
                viewHolder.holderCardView.setOnClickListener { v: View ->
                    itemClickListener!!.onItemClick(
                        v,
                        productsFiltered!![position], position
                    )
                }
            }

        }
    }

    override fun getItemCount(): Int {
        if (productArrayList != null) {
            return productsFiltered!!.size
        }
        return 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    productsFiltered = products
                } else {
                    val filteredList = ArrayList<AdEntity>()
                    if (productArrayList != null) {
                        for (row in productArrayList) {


                            if (row.wilaya.toLowerCase().contains(charString.toLowerCase()) || row.ownerName.toLowerCase().contains(
                                    charString.toLowerCase()
                                ) || row.category.toLowerCase().contains(charString.toLowerCase())
                            ) {
                                filteredList.add(row)
                            }
                        }
                    }

                    productsFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = productsFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                productsFiltered = filterResults.values as ArrayList<AdEntity>
                notifyDataSetChanged()
            }
        }
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.productImageView
        internal var productName: TextView = view.productNameTextView
        internal var productowner: TextView = view.OwnerNameTextView
        internal var wilaya: TextView = view.WilayaText
        internal var holderCardView: CardView = view.cardViewHolder

    }
}