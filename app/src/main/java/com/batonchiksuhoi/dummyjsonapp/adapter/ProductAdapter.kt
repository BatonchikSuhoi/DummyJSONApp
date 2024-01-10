package com.batonchiksuhoi.dummyjsonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batonchiksuhoi.dummyjsonapp.R
import com.batonchiksuhoi.dummyjsonapp.databinding.ListItemBinding
import com.batonchiksuhoi.dummyjsonapp.retrofit.Product
import com.squareup.picasso.Picasso

class ProductAdapter(val listener: Listener) : ListAdapter<Product, ProductAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)

        fun bind(product: Product, listener: Listener) = with(binding){
            title.text = product.title
            description.text = product.description
            Picasso.get().load(product.thumbnail).into(binding.thumbnail)
            itemView.setOnClickListener{
                listener.onClick(product)
            }
        }
    }
    class Comparator : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    interface Listener{
        fun onClick(product: Product)
    }
}