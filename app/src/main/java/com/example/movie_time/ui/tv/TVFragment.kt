package com.example.movie_time.ui.tv

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movie_time.api.MovieApi.Companion.WEEK
import com.example.movie_time.databinding.FragmentTvBinding
import com.example.movie_time.ui.home.HeadAdapter
import com.example.movie_time.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TVFragment : Fragment() {

    private val viewModel: TVViewModel by viewModels()
    private val viewModelHome: HomeViewModel by viewModels()

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

//        val headAdapter = HeadAdapter()
//        binding.viewPager.adapter = headAdapter
//        viewModelHome.getTrending(WEEK)
//
//        viewModelHome.popularListData.observe(viewLifecycleOwner) {
//            headAdapter.submitList(it)
//        }

//        binding.apply {
//            viewPager.beginFakeDrag()
//            viewPager.fakeDragBy(-50f)
//            viewPager.endFakeDrag()
//
//        }

//        var counter = 1
//        binding.root.setOnClickListener {
//            if (counter >= 20)
//                counter = 0
//            binding.viewPager.setCurrentItem(counter,true)
//            counter++
//        }

//        viewModel.counter.observe(viewLifecycleOwner){
//            binding.viewPager.setCurrentItem(it, true)
//            viewModel.pageSwitcher(it)
//        }

//        val youTubePlayerView = binding.youtubePlayerView
//        lifecycle.addObserver(youTubePlayerView)
//
//        val container = youTubePlayerView.getPlayerUiController()
//
//        val videoId = "IXFxXY1-fBY"
//
//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                youTubePlayer.loadVideo(videoId, 0f)
//                youTubePlayer.pause()
//            }
//
//            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
//                super.onError(youTubePlayer, error)
//                Log.i("TAG", "onError: $error")
//            }
//
//        })
//        viewModel.searchListData.observe(viewLifecycleOwner) {
//            binding.textView2.text = it.toString()
//        }
    }




}