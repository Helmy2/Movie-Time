package com.example.movie_time.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movie_time.R

class TVFragment : Fragment() {

    private lateinit var notificationsViewModel: TVViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(TVViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tv, container, false)

        return root
    }
}