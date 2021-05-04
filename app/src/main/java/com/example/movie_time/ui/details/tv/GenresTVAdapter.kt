package com.example.movie_time.ui.details.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.data.movie.Genre
import com.example.movie_time.databinding.ItemGenresBinding

class GenresTVAdapter :
    ListAdapter<Genre, GenresTVAdapter.GenresTVViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresTVViewHolder {
        val binding =
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresTVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresTVViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class GenresTVViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.apply {
                textViewGenre.text = genre.name
                root.setOnClickListener {
                    val action =
                        TVDetailsFragmentDirections.actionTVDetailsFragment2ToListFragment(
                            genre.id,
                            genre.name,
                            TV
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