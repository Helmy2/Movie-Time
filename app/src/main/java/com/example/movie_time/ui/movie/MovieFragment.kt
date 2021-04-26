package com.example.movie_time.ui.movie

import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings.PluginState
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movie_time.databinding.FragmentMovieBinding
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private var _binding: FragmentMovieBinding? = null
    private val binding: FragmentMovieBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val mediaControl =MediaController(requireContext())
//        mediaControl.setAnchorView(binding.videoView)
//
//        val uri = Uri.parse("https://cdn.videvo.net/videvo_files/video/free/2017-03/small_watermarked/170216A_016_BoyPlayingPiano2_1080p_preview.webm")
//
//        binding.videoView.setMediaController(mediaControl)
//        videoView.setVideoURI(uri)
//        videoView.requestFocus()
//        videoView.start()


    }
}