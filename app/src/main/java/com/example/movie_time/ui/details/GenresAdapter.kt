package com.example.movie_time.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.data.movie.Genre
import com.example.movie_time.databinding.ItemGenresBinding

class GenresAdapter :
    ListAdapter<Genre, GenresAdapter.GenresMovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresMovieViewHolder {
        val binding =
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class GenresMovieViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.apply {
                textViewGenre.text = genre.name
                root.setOnClickListener {
                    val action =
                        DetailsFragmentDirections.actionMovieDetailsFragmentToListFragment(
                            genre.id,
                            genre.name,
                            MOVIE
                        )
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