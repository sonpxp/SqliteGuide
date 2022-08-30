package com.cloudxanh.examplesqlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cloudxanh.examplesqlite.databinding.ItemFeedBinding
import com.cloudxanh.examplesqlite.model.Feed


/**
 * Created by sonpxp on 8/31/2022.
 * Email: sonmob202@gmail.com
 * https://developer.android.com/training/data-storage/sqlite
 */

class FeedAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Feed, FeedAdapter.ViewHolder>(DiffCallback) {

    interface OnItemClickListener {
        fun onFeedItemClick(feed: Feed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Feed) {
            binding.apply {
                tvTitle.text = item.title
                tvSubTitle.text = item.subtitle

                root.setOnClickListener {
                    listener.onFeedItemClick(item)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Feed>() {

            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Feed,
                newItem: Feed
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}