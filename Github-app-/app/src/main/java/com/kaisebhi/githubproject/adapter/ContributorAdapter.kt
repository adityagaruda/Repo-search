package com.kaisebhi.githubproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.data.network.ContributorModel
import com.kaisebhi.githubproject.databinding.ContributorsListItemsBinding

class ContributorAdapter() : androidx.recyclerview.widget.ListAdapter<ContributorModel, ContributorAdapter.ViewHolder>(ContributorAdapter.DiffUtils()) {
    val TAG = "cont.kt"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContributorAdapter.ViewHolder {
        val binding: ContributorsListItemsBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.contributors_list_items,
        parent,
        false
    )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(val binding: ContributorsListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ContributorModel) {
            binding.nameTv.text = data.name
        }
    }

    class DiffUtils : DiffUtil.ItemCallback<ContributorModel>() {
        override fun areItemsTheSame(oldItem: ContributorModel, newItem: ContributorModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContributorModel, newItem: ContributorModel): Boolean {
            return oldItem == newItem
        }
    }
}