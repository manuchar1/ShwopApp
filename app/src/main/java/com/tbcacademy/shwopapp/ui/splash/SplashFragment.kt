package com.tbcacademy.shwopapp.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.SplashFragmentBinding

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }

        }, 3000)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)

    }


}