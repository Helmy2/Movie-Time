package com.example.movie_time.ui.details.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie_time.R
import com.example.movie_time.api.MovieApi
import com.example.movie_time.data.movie.Poster
import com.example.movie_time.databinding.ItemImageBinding

class ImageAdapter :
    ListAdapter<Poster, ImageAdapter.CastMovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMovieViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CastMovieViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Poster) {
            binding.apply {

                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                cast.filePath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_placeholder_background)
                    .into(imageView)

            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Poster>() {
        override fun areItemsTheSame(oldItem: Poster, newItem: Poster) =
            oldItem.filePath == newItem.filePath

        override fun areContentsTheSame(oldItem: Poster, newItem: Poster) =
            oldItem == newItem
    }
}