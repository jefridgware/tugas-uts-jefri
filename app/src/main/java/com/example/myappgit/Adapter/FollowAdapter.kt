package com.example.myappgit.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myappgit.databinding.ItemReviewBinding
import com.example.myappgit.response.ItemsItem

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.UserViewHolder>() {

    private val list = ArrayList<ItemsItem>()

    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: List<ItemsItem>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemReviewBinding) : RecyclerView
    .ViewHolder(binding.root) {
        fun bind(user:ItemsItem) {

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                tvItemName.text = user.login
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .override(60, 60)
                    .centerCrop()
                    .into(imgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])

    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }

}