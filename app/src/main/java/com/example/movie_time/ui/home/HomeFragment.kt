package com.example.movie_time.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_time.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

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

        val listsAdapter = ListsAdapter()

        binding.apply {
            recyclerViewLists.apply {
                adapter = listsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.restaurants.observe(viewLifecycleOwner) {
            if (it.error == null) {
                listsAdapter.submitList(
                    listOf(
                        it.data,
                        it.data,
                        it.data,
                        it.data,
                        it.data,
                        it.data,
                        it.data
                    )
                )
            }
            else{
                binding.textView.text = it.error.localizedMessage
            }
        }
    }
}