package com.example.movie_time.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_time.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
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
        val movieAdapter2 = MovieAdapter()

        binding.apply {
            recyclerViewMovies.apply {
                adapter = movieAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            recyclerViewMovies2.apply {
                adapter = movieAdapter2
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            recyclerViewHead.apply {
                adapter = headAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        binding.buttonRefresh.setOnClickListener {
            lifecycleScope.launch {
                viewModel.refreshDelay()
            }
            it.visibility = View.GONE
            it.visibility = View.VISIBLE
        }

        viewModel.headListData.observe(viewLifecycleOwner) {
            headAdapter.submitList(it)
            movieAdapter.submitList(it)
            movieAdapter2.submitList(it)
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
                    textViewPopular2.visibility = View.GONE
                }

            } else {
                binding.apply {
                    buttonRefresh.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    textViewPopular.visibility = View.VISIBLE
                    textViewPopular2.visibility = View.VISIBLE
                }
            }
        }
    }

}