package com.example.movie_time.ui.details.movie

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
import com.example.movie_time.databinding.ItemMovieBinding

class RecommendationsMovieAdapter() :
    ListAdapter<Result, RecommendationsMovieAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.apply {

                if (result.type == MovieApi.MOVIE) {
                    textView.text = result.title
                    textViewReleaseDate.text = result.releaseDate
                } else {
                    textView.text = result.name
                    textViewReleaseDate.text = result.firstAirDate
                }

                textViewVote.text = String.format("%.1f", result.voteAverage)
                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                result.posterPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                root.setOnClickListener {
                    val action =
                        MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                            result.id,
                            result.title
                        )
                    it.findNavController().navigate(action)
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