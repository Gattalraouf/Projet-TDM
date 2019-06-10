package com.gattal.asta.mobileproject.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gattal.asta.mobileproject.data.Product
import com.gattal.asta.mobileproject.R
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerViewAdapter(private val productArrayList: List<Product>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private var products: List<Product>? = productArrayList
    private var productsFiltered: List<Product>? = productArrayList
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Product, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val product = this.productsFiltered!![position]

            viewHolder.productName.text = product.name
            viewHolder.productowner.text = product.owner.name

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
                    val filteredList = ArrayList<Product>()
                    if (productArrayList != null) {
                        for (row in productArrayList) {


                            if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.owner.name.contains(
                                    charSequence
                                )
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
                productsFiltered = filterResults.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.productImageView
        internal var productName: TextView = view.productNameTextView
        internal var productowner: TextView = view.OwnerNameTextView
        internal var holderCardView: CardView = view.cardViewHolder

    }
}