package com.raka.suitmediatest.ui.secondscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raka.suitmediatest.R
import com.raka.suitmediatest.data.local.DataFlashSale
import com.raka.suitmediatest.databinding.ItemFlashsaleBinding

class FlashSaleAdapter(private val data: ArrayList<DataFlashSale>): RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemFlashsaleBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFlashsaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]

        Glide.with(holder.itemView.context)
            .load(data)
            .into(holder.binding.imageFlashSale)
    }

}