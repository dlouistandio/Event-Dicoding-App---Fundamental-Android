package com.example.eventdicoding.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.remote.response.ListEventsItem
import com.example.eventdicoding.databinding.EventCardBinding
import com.example.eventdicoding.ui.detailsEvent.DetailsEventActivity
import com.example.eventdicoding.ui.adapter.ListEventAdapter.MyViewHolder.Companion.DIFF_CALLBACK

class ListEventAdapter: ListAdapter<ListEventsItem, ListEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

    }

    class MyViewHolder(val binding: EventCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListEventsItem) {
            binding.cardDescription.text = data.name
            Glide.with(this.itemView.context)
                .load(data.imageLogo)
                .into(binding.cardImage)

            itemView.setOnClickListener() {
                val moveWithDataIntent = Intent(itemView.context, DetailsEventActivity::class.java)
                moveWithDataIntent.putExtra(DetailsEventActivity.ID, data.id)
                itemView.context.startActivity(moveWithDataIntent)
            }
        }


        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
                override fun areItemsTheSame(
                    oldItem: ListEventsItem,
                    newItem: ListEventsItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ListEventsItem,
                    newItem: ListEventsItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}