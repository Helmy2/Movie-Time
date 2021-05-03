package com.example.movie_time.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie_time.R
import com.example.movie_time.api.MovieApi
import com.example.movie_time.data.Result
import com.example.movie_time.databinding.ItemHeadBinding

class HeadAdapter :
    ListAdapter<Result, HeadAdapter.HeadViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadViewHolder {
        val binding =
            ItemHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class HeadViewHolder(private val binding: ItemHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.apply {

                if (result.title != null)
                    textViewTitle.text = result.title
                else
                    textViewTitle.text = result.name

                textViewVote.text = result.voteAverage.toString()
                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                result.posterPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewPoster)

                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                result.backdropPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewBackdrop)

                root.setOnClickListener {
                    if (result.type == MovieApi.MOVIE) {
                        val action =
                            HomeFragmentDirections.actionNavigationHomeToMovieDetailsFragment(
                                result.id,
                                result.title
                            )
                        it.findNavController().navigate(action)
                    } else {
                        val action =
                            HomeFragmentDirections.actionNavigationHomeToTVDetailsFragment2(
                                result.id,
                                result.name
                            )
                        it.findNavController().navigate(action)
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem
    }

}