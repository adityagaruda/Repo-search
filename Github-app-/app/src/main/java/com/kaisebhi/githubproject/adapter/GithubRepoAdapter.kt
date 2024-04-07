package com.kaisebhi.githubproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.databinding.ListItemBinding
import com.kaisebhi.githubproject.ui.details.RentDetailsActivity

class GithubRepoAdapter(private val context: Context) :
    PagingDataAdapter<AllRepoEntity, GithubRepoAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDataModel = getItem(position) ?: return  // Return early if the item is null
        holder.bind(itemDataModel)
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllRepoEntity) {
            binding.itemName.text = data.name
            binding.desc.text = data.description
            binding.root.setOnClickListener {
                val intent = Intent(context, RentDetailsActivity::class.java).apply {
                    putExtra("url", data.url.toString() + "")
                }
                context.startActivity(intent)
            }

            data.description?.let {
                if (it.length > 30) binding.desc.text = "${it.substring(0, 30)}..."
            }

            data.owner?.avatar_url?.let { avatarUrl ->
                Glide.with(context).load(avatarUrl).into(binding.itemImg)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AllRepoEntity>() {
        override fun areItemsTheSame(oldItem: AllRepoEntity, newItem: AllRepoEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: AllRepoEntity, newItem: AllRepoEntity): Boolean {
            return oldItem == newItem
        }
    }
}
