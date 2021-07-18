package com.tbcacademy.shwopapp.ui.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.FragmentFirstScreenBinding


class FirstScreen : BaseFragment<FragmentFirstScreenBinding>(FragmentFirstScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

    }



}