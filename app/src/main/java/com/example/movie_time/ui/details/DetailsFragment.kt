package com.example.movie_time.ui.details

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
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
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genresAdapter = GenresAdapter()
        val castAdapter = CastAdapter()
        val recommendationsAdapter = RecommendationsAdapter()
        val imageAdapter = ImageAdapter()

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
            recyclerViewImage.apply {
                adapter = imageAdapter
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

            images.observe(viewLifecycleOwner) {
                imageAdapter.submitList(it)
                if (it.isNotEmpty())
                    binding.cardViewImage.visibility = View.VISIBLE
            }

            if (args.type == MOVIE) {
                detailsData.observe(viewLifecycleOwner) {
                    binding.apply {
                        constraintLayout.visibility = View.VISIBLE
                        genresAdapter.submitList(it.genres)

                        textViewOverview.text = it.overview
                        textViewTitle.text = it.title
                        textViewVote.text = it.voteAverage.toString()

                        Glide.with(this@DetailsFragment)
                            .load(
                                MovieApi.IMAGE_URL +
                                        it.posterPath
                            )
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_placeholder_photo)
                            .into(imageViewPoster)

                        Glide.with(this@DetailsFragment)
                            .load(
                                MovieApi.IMAGE_URL_ORIGINAL +
                                        it.backdropPath
                            )
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_placeholder_background)
                            .into(imageViewBackdrop)

                    }
                }
            }else{
                tvDetailsData.observe(viewLifecycleOwner) {
                binding.apply {
                    constraintLayout.visibility = View.VISIBLE
                    genresAdapter.submitList(it.genres)

                    textViewOverview.text = it.overview
                    textViewTitle.text = it.name
                    textViewVote.text = it.voteAverage.toString()

                    Glide.with(this@DetailsFragment)
                        .load(
                            MovieApi.IMAGE_URL +
                                    it.posterPath
                        )
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_placeholder_photo)
                        .into(imageViewPoster)

                    Glide.with(this@DetailsFragment)
                        .load(
                            MovieApi.IMAGE_URL_ORIGINAL +
                                    it.backdropPath
                        )
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_placeholder_background)
                        .into(imageViewBackdrop)
                }
            }

            }

        }
        internetErrorHandling()
    }

    @SuppressLint("NewApi")
    private fun internetErrorHandling() {
        val connectivityManager =
            requireActivity().getSystemService(ConnectivityManager::class.java)

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.refresh(args.id,args.type)
            }

            override fun onLost(network: Network) {

            }
        })
    }

}
