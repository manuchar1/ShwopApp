package com.tbcacademy.shwopapp.ui.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.data.UserPreference
import com.tbcacademy.shwopapp.databinding.FragmentThirdScreenBinding


class ThirdScreen : BaseFragment<FragmentThirdScreenBinding>(FragmentThirdScreenBinding::inflate) {

    private lateinit var sharedPreferences: UserPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finish.setOnClickListener {
            sharedPreferences = UserPreference(requireContext())
            sharedPreferences.saveUserSession(true)
            sharedPreferences.saveToken("onBoard")

            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }
    }
}