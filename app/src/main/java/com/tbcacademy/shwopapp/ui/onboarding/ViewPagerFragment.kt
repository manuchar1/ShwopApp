package com.tbcacademy.shwopapp.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.FragmentViewPagerBinding
import com.tbcacademy.shwopapp.ui.onboarding.adapter.ViewPagerAdapter
import com.tbcacademy.shwopapp.ui.onboarding.screens.FirstScreen
import com.tbcacademy.shwopapp.ui.onboarding.screens.SecondScreen
import com.tbcacademy.shwopapp.ui.onboarding.screens.ThirdScreen


class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPagerFragments()

    }

    private fun initPagerFragments() {
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()

        )

        val adapter = ViewPagerAdapter(
            fragmentList, requireActivity().supportFragmentManager, lifecycle
        )
        binding.viewPager.adapter = adapter
    }



}