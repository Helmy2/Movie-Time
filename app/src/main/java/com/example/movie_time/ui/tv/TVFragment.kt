package com.example.movie_time.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movie_time.R
import com.example.movie_time.databinding.FragmentMovieBinding
import com.example.movie_time.databinding.FragmentTvBinding
import com.example.movie_time.ui.movie.MovieViewModel

class TVFragment : Fragment() {

    private val viewModel: TVViewModel by viewModels()

    private var _binding: FragmentTvBinding? = null
    private val binding: FragmentTvBinding
        get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}