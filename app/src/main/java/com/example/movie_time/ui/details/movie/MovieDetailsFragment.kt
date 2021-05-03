package com.example.movie_time.ui.details.movie

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
import com.example.movie_time.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh(args.id)

        val genresAdapter = GenresMovieAdapter()
        val castAdapter = CastMovieAdapter()
        val recommendationsAdapter = RecommendationsMovieAdapter()

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
                textViewTitle.text = it.title
                textViewVote.text = it.voteAverage.toString()

                Glide.with(this@MovieDetailsFragment)
                    .load(
                        MovieApi.IMAGE_URL +
                                it.posterPath
                    )
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewPoster)

                Glide.with(this@MovieDetailsFragment)
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
