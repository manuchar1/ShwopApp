package com.tbcacademy.shwopapp.ui.bottom_navigation.home

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbcacademy.shwopapp.databinding.HomeFragmentBinding
import com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product.BasePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseHomeFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {


    override val postProgressBar: ProgressBar
        get() = binding.progressBarr

    override val baseViewModel: BasePostViewModel
        get() {
            val viewModel: HomeViewModel by viewModels()
            return viewModel


        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())
        itemAnimator = null

    }


}