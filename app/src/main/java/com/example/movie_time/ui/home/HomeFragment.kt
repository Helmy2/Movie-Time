package com.example.movie_time.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movie_time.api.MovieApi.Companion.DAY
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.api.MovieApi.Companion.WEEK
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
        val headAdapter = HeadAdapter()

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
            viewPager.adapter = headAdapter
            chipPopularMovie.setOnClickListener {
                viewModel.getPopular(MOVIE)
            }
            chipPopularTV.setOnClickListener {
                viewModel.getPopular(TV)
            }

            chipTrendingToday.setOnClickListener {
                viewModel.getTrending(DAY)
            }
            chipTrendingThisWeek.setOnClickListener {
                viewModel.getTrending(WEEK)
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.pagePosition(position)
                }
            })
        }

        viewModel.apply {
            trendingListData.observe(viewLifecycleOwner) {
                trendingAdapter.submitList(it)
            }
            popularListData.observe(viewLifecycleOwner) {
                popularAdapter.submitList(it)
            }

            trendingListData.observe(viewLifecycleOwner) {
                headAdapter.submitList(it)
            }

            counter.observe(viewLifecycleOwner) {
                binding.viewPager.setCurrentItem(it, true)
                pageSwitcher()
            }

            popularType.observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        MOVIE -> {
                            chipPopularMovie.isChecked = true
                            chipPopularTV.isChecked = false
                        }
                        TV -> {
                            chipPopularTV.isChecked = true
                            chipPopularMovie.isChecked = false
                        }
                    }
                }
            }

            trendingType.observe(viewLifecycleOwner) {
                binding.apply {
                    when (it) {
                        DAY -> {
                            chipTrendingToday.isChecked = true
                            chipTrendingThisWeek.isChecked = false
                        }
                        WEEK -> {
                            chipTrendingThisWeek.isChecked = true
                            chipTrendingToday.isChecked = false
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

