package com.raka.suitmediatest.ui.thirdscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raka.suitmediatest.data.remote.response.DataItem
import com.raka.suitmediatest.databinding.ItemUserBinding

class UserAdapter: PagingDataAdapter<DataItem, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = getItem(position)
            holder.binding.tvEmail.text = data?.email
            holder.binding.tvUsername.text = data?.firstName + data?.lastName
            Glide.with(holder.binding.root)
                .load(data?.avatar)
                .centerCrop()
                .into(holder.binding.ivAvatar)

            holder.binding.root.setOnClickListener{
                onItemClickCallback.onItemClicked(data!!)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface OnClickCallback{
        fun onItemClicked(data: DataItem)
    }
}