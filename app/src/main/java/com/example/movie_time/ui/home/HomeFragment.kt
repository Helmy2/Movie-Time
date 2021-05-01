package com.example.movie_time.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trendingAdapter = MovieAdapter()
        val popularAdapter = MovieAdapter()

        binding.apply {
            recyclerViewMovies.apply {
                adapter = popularAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            recyclerViewHead.apply {
                adapter = trendingAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            chipMovie.setOnClickListener {
                viewModel.getPopular(MOVIE)
            }
            chipTV.setOnClickListener {
                viewModel.getPopular(TV)
            }
        }

        viewModel.apply {
            trendingListData.observe(viewLifecycleOwner) {
                trendingAdapter.submitList(it)
            }
            popularListData.observe(viewLifecycleOwner) {
                popularAdapter.submitList(it)
            }

            popularType.observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        MOVIE -> {
                            chipMovie.isChecked = true
                            chipTV.isChecked = false
                        }
                        TV -> {
                            chipTV.isChecked = true
                            chipMovie.isChecked = false
                        }
                    }
                }
            }

        }

        internetErrorHandling()
    }

    private fun internetErrorHandling() {
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    buttonRefresh.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    constraintLayout.visibility = View.GONE
                }

            } else {
                binding.apply {
                    buttonRefresh.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    constraintLayout.visibility = View.VISIBLE
                }
            }
        }
        binding.buttonRefresh.setOnClickListener {
            lifecycleScope.launch {
                viewModel.refreshDelay()
            }
            it.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

}

