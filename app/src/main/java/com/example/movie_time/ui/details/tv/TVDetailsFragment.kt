package com.example.movie_time.ui.details.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie_time.R
import com.example.movie_time.api.MovieApi
import com.example.movie_time.databinding.FragmentTvDetailsBinding
import com.example.movie_time.ui.details.movie.CastMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVDetailsFragment : Fragment() {

    private var _binding: FragmentTvDetailsBinding? = null
    private val binding: FragmentTvDetailsBinding
        get() = _binding!!

    private val args: TVDetailsFragmentArgs by navArgs()

    private val viewModel: TVDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh(args.id)

        val genresAdapter = GenresTVAdapter()
        val castAdapter = CastTVAdapter()
        val recommendationsAdapter = RecommendationsTVAdapter()

        binding.apply {
            recyclerViewGenres.apply {
                adapter = genresAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            recyclerViewCrew.apply {
                adapter = castAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            recyclerViewRecommendations.apply {
                adapter = recommendationsAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.apply {
            castData.observe(viewLifecycleOwner) {
                castAdapter.submitList(it)
                if (it.isNotEmpty())
                    binding.cardViewFullCast.visibility = View.VISIBLE
            }
            recommendationsData.observe(viewLifecycleOwner) {
                recommendationsAdapter.submitList(it)
                if (it.isNotEmpty())
                    binding.cardViewSimilar.visibility = View.VISIBLE
            }
        }

        viewModel.detailsData.observe(viewLifecycleOwner) {
            binding.apply {
                genresAdapter.submitList(it.genres)
                textViewOverview.text = it.overview
                textViewTitle.text = it.name
                textViewVote.text = it.voteAverage.toString()

                Glide.with(this@TVDetailsFragment)
                    .load(
                        MovieApi.IMAGE_URL +
                                it.posterPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewPoster)

                Glide.with(this@TVDetailsFragment)
                    .load(
                        MovieApi.IMAGE_URL_ORIGINAL +
                                it.backdropPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewBackdrop)

            }
        }
        internetErrorHandling()
    }

    private fun internetErrorHandling() {
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    constraintLayout.visibility = View.GONE
                }
            } else {
                binding.apply {
                    constraintLayout.visibility = View.VISIBLE
                }
            }
        }
    }

}