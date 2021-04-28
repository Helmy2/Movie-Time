package com.example.movie_time.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_time.data.MovieResponse
import com.example.movie_time.databinding.ListsItemBinding


class ListsAdapter() :
    ListAdapter<MovieResponse, ListsAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ListsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MovieViewHolder(private val binding: ListsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieResponse: MovieResponse) {
            binding.apply {
                textViewCategory.text = movieResponse.category
                val movieAdapter = MovieAdapter()
                recyclerViewMovies.apply {
                    adapter = movieAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                }
                movieAdapter.submitList(movieResponse.results)
            }
        }
    }


class DiffCallback : DiffUtil.ItemCallback<MovieResponse>() {
    override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
        oldItem.category == newItem.category

    override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
        oldItem == newItem
}
}