package com.tbcacademy.shwopapp.ui.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.FragmentSecondBinding


class SecondScreen : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.tvNext2.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

}