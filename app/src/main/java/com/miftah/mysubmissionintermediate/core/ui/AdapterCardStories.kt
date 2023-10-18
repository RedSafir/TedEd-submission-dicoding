package com.miftah.mysubmissionintermediate.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.databinding.CardStoryBinding

class AdapterCardStories : ListAdapter<ListStoryItem, AdapterCardStories.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnClickListener

    inner class ViewHolder(val binding : CardStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: ListStoryItem) {
            Glide.with(binding.root)
                .load(listStoryItem.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = listStoryItem.name
            binding.tvItemDesc.text = listStoryItem.description
        }

        fun callCard(listStoryItem: ListStoryItem) {
            onItemClickCallback.onClickCard(listStoryItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listStoryItem = getItem(position)
        holder.bind(listStoryItem)
        holder.itemView.setOnClickListener {
            holder.callCard(listStoryItem)
        }
    }

    fun setOnClickCallback(call: OnClickListener) {
        this.onItemClickCallback = call
    }

    interface OnClickListener {
        fun onClickCard(friendItem: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}