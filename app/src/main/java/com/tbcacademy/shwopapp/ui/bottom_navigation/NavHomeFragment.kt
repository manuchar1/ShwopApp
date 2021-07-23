package com.tbcacademy.shwopapp.ui.bottom_navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.FragmentNavHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHomeFragment : BaseFragment<FragmentNavHomeBinding>(FragmentNavHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)



        }

    }

}