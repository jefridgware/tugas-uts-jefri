    package com.example.myappgit.ui

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.ListAdapter
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.myappgit.databinding.ItemReviewBinding
    import com.example.myappgit.response.ItemsItem

    class ReviewAdapter : ListAdapter<ItemsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

        private lateinit var onItemClickCallback: OnItemClickCallback
        private var list = ArrayList<ItemsItem>()

        interface OnItemClickCallback {
            fun onItemClicked(userActivity: ItemsItem)
        }

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val review = getItem(position)
            holder.bind(review)
        }

        inner class MyViewHolder(val binding: ItemReviewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(review: ItemsItem) {
                binding.root.setOnClickListener {
                    onItemClickCallback.onItemClicked(review)
                }
                binding.tvItemName.text = "${review.login}"
                Glide.with(binding.imgItemPhoto.context)
                    .load(review.avatarUrl)
                    .circleCrop()
                    .into(binding.imgItemPhoto)
            }
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
                override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
