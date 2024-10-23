package com.example.eventdicoding.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eventdicoding.R
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.databinding.EventCardBinding
import com.example.eventdicoding.ui.adapter.FavoriteEventAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.example.eventdicoding.ui.detailsEvent.DetailsEventActivity

class FavoriteEventAdapter :
    ListAdapter<EventEntity, FavoriteEventAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val event = getItem(position)
        holder.bind(event)
    }


    class MyViewHolder(val binding: EventCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.cardDescription.text = event.name
            Glide.with(this.itemView.context)
                .load(event.mediaCover)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.cardImage)

            itemView.setOnClickListener() {
                val moveWithDataIntent = Intent(itemView.context, DetailsEventActivity::class.java)
                moveWithDataIntent.putExtra(DetailsEventActivity.ID, event.id)
                itemView.context.startActivity(moveWithDataIntent)
            }
        }


        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
                override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: EventEntity,
                    newItem: EventEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}