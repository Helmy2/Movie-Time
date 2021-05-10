package com.example.movie_time.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie_time.R
import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.data.Result
import com.example.movie_time.databinding.ItemListBinding

class ListAdapter() :
    PagingDataAdapter<Result, com.example.movie_time.ui.list.ListAdapter.ListViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.apply {

                if (result.type == MOVIE) {
                    textView.text = result.title
                    textViewReleaseDate.text = result.releaseDate
                } else {
                    textView.text = result.name
                    textViewReleaseDate.text = result.firstAirDate
                }

                textViewVote.text = result.voteAverage.toString()
                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                result.posterPath
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_placeholder_photo)
                    .into(imageView)

                root.setOnClickListener {
                    if (result.type == MOVIE) {
                        val action =
                            ListFragmentDirections.actionListFragmentToMovieDetailsFragment(
                                result.id,
                                result.title
                            )
                        it.findNavController().navigate(action)
                    } else {
                        val action =
                            ListFragmentDirections.actionListFragmentToTVDetailsFragment2(
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