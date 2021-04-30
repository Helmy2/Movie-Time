package com.example.movie_time.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_time.R
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

        val headAdapter = HeadAdapter()
        val movieAdapter = MovieAdapter()

        binding.apply {
            recyclerViewMovies.apply {
                adapter = movieAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            recyclerViewHead.apply {
                adapter = headAdapter
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
            headListData.observe(viewLifecycleOwner) {
                headAdapter.submitList(it)
            }
            popularListData.observe(viewLifecycleOwner) {
                movieAdapter.submitList(it)
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
                    textViewPopular.visibility = View.GONE
                    constraintLayoutPopular.visibility = View.GONE
                }

            } else {
                binding.apply {
                    buttonRefresh.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    textViewPopular.visibility = View.VISIBLE
                    constraintLayoutPopular.visibility = View.VISIBLE
                }
            }
        }

        binding.buttonRefresh.setOnClickListener {
            lifecycleScope.launch {
                viewModel.refreshDelay()
            }
            it.visibility = View.GONE
            it.visibility = View.VISIBLE
        }
    }

}

