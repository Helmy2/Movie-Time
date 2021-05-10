package com.example.movie_time.ui.home

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    private val viewModel: HomeViewModel by viewModels()

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
                headAdapter.submitList(it)
            }
            popularListData.observe(viewLifecycleOwner) {
                popularAdapter.submitList(it)
            }
            counter.observe(viewLifecycleOwner) {
                binding.viewPager.setCurrentItem(it, true)
                pageSwitcher()
            }
        }

        internetErrorHandling()
    }

    @SuppressLint("NewApi")
    private fun internetErrorHandling() {
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    noInternet.visibility = View.VISIBLE
                    constraintLayout.visibility = View.GONE
                }

            } else {
                binding.apply {
                    noInternet.visibility = View.GONE
                    constraintLayout.visibility = View.VISIBLE
                }
            }
        }

        val connectivityManager = requireActivity().getSystemService(ConnectivityManager::class.java)

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.refresh()
            }
            override fun onLost(network: Network) {

            }
        })
    }

}

