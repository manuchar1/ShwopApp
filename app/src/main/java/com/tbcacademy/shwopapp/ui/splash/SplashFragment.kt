package com.tbcacademy.shwopapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.data.UserPreference
import com.tbcacademy.shwopapp.databinding.SplashFragmentBinding

class SplashFragment : BaseFragment<SplashFragmentBinding>(SplashFragmentBinding::inflate) {

    private lateinit var sharedPreferences: UserPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            skipOnboardFragments()
        }, 3000)
    }

    private fun skipOnboardFragments() {

        sharedPreferences = UserPreference(requireContext())

        when (sharedPreferences.token()) {

            "onBoard" -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            "login" -> findNavController().navigate(R.id.action_splashFragment_to_navHomeFragment)
            else -> findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)

        }

    }

}