package com.example.movie_time.ui.details.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.data.movie.Genre
import com.example.movie_time.databinding.ItemGenresBinding

class GenresMovieAdapter :
    ListAdapter<Genre, GenresMovieAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MovieViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.apply {
                textViewGenre.text = genre.name
                root.setOnClickListener {
                    val action =
                        MovieDetailsFragmentDirections.actionMovieDetailsFragmentToListFragment(genre.id,genre.name,MOVIE)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem == newItem
    }
}