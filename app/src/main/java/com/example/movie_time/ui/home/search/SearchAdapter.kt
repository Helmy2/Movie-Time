package com.example.movie_time.ui.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie_time.R
import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.PERSON
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.data.Result
import com.example.movie_time.databinding.ItemMovieBinding
import com.example.movie_time.databinding.ItemSearchBinding
import com.example.movie_time.ui.home.HomeFragmentDirections
import com.example.movie_time.ui.search.SearchFragmentDirections

class SearchAdapter() :
    ListAdapter<Result, SearchAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MovieViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.apply {

                when (result.type) {
                    MOVIE -> {
                        imageStarRate.visibility = View.VISIBLE
                        textViewVote.visibility = View.VISIBLE
                        textViewReleaseDate.visibility = View.VISIBLE

                        textView.text = result.title
                        textViewReleaseDate.text = result.releaseDate
                        textViewVote.text = result.voteAverage.toString()
                        Glide.with(itemView)
                            .load(
                                MovieApi.IMAGE_URL +
                                        result.posterPath
                            )
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_placeholder_photo)
                            .into(imageView)
                    }
                    TV -> {
                        imageStarRate.visibility = View.VISIBLE
                        textViewVote.visibility = View.VISIBLE
                        textViewReleaseDate.visibility = View.VISIBLE

                        textView.text = result.name
                        textViewReleaseDate.text = result.firstAirDate
                        textViewVote.text = result.voteAverage.toString()
                        Glide.with(itemView)
                            .load(
                                MovieApi.IMAGE_URL +
                                        result.posterPath
                            )
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_placeholder_photo)
                            .into(imageView)
                    }
                    PERSON -> {
                        imageStarRate.visibility = View.GONE
                        textViewVote.visibility = View.GONE
                        textViewReleaseDate.visibility = View.GONE

                        textView.text = result.name
                        Glide.with(itemView)
                            .load(
                                MovieApi.IMAGE_URL +
                                        result.profilePath
                            )
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_placeholder_photo)
                            .into(imageView)
                    }
                }

                root.setOnClickListener {
                    when (result.type) {
                        MOVIE -> {
                            val action =
                                SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(
                                    result.id,
                                    result.title,
                                    MOVIE
                                )
                            it.findNavController().navigate(action)
                        }
                        TV -> {
                            val action =
                                SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(
                                    result.id,
                                    result.name,
                                    TV
                                )
                            it.findNavController().navigate(action)
                        }
                        PERSON -> {
                            val action =
                                SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(
                                    result.id,
                                    result.name,
                                    PERSON
                                )
                            it.findNavController().navigate(action)
                        }
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