package com.example.movie_time.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_time.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

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

        val listsAdapter = ListsAdapter()

        binding.apply {
            recyclerViewLists.apply {
                adapter = listsAdapter
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                setHasFixedSize(true)
            }

            button.setOnClickListener {
                viewModel.refresh()
                binding.button.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

        }
        viewModel.restaurants.observe(viewLifecycleOwner) {
            listsAdapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textView.text = it
                binding.button.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }else{
                binding.textView.text = ""
                binding.button.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}