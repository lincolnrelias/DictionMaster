package com.dictionmaster.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dictionmaster.R

class SplashFragment : Fragment() {
    private val SPLASH_SCREEN_DURATION = 3000L // 3 seconds

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to SearchFragment
            findNavController().navigate(R.id.action_splashFragment_to_searchFragment)
        }, SPLASH_SCREEN_DURATION)
    }
}
