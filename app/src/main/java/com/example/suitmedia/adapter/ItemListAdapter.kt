package com.example.suitmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmedia.data.User
import com.example.suitmedia.databinding.ItemListBinding
import javax.inject.Inject

class ItemListAdapter @Inject constructor() : PagingDataAdapter<User, ItemListAdapter.ViewHolder>(DIFF_CALLBACK) {

    lateinit var onItemClick: ((User) -> Unit)

    inner class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.apply {
                tvNameUser.text = "${user.firstName} ${user.lastName}"
                tvEmail.text = user.email

                Glide.with(itemView)
                    .load(user.avatar)
                    .into(cvProfile)
            }
            itemView.setOnClickListener {
                onItemClick.invoke(user)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
        }
    }
}