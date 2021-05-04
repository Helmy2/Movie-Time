package com.example.movie_time.ui.details.tv

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
import com.example.movie_time.api.MovieApi.Companion.CREDITS
import com.example.movie_time.data.movie.Cast
import com.example.movie_time.databinding.ItemCastBinding

class CastTVAdapter :
    ListAdapter<Cast, CastTVAdapter.CastTVViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastTVViewHolder {
        val binding =
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastTVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastTVViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CastTVViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.apply {
                textViewName.text = cast.name

                Glide.with(itemView)
                    .load(
                        MovieApi.IMAGE_URL +
                                cast.profilePath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_placeholder_user)
                    .into(imageView)

                root.setOnClickListener {
                    val action =
                        TVDetailsFragmentDirections.actionTVDetailsFragment2ToListFragment(
                            cast.id, cast.name, CREDITS
                        )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast) =
            oldItem == newItem
    }
}