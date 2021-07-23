package com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.databinding.AddProductFragmentBinding

class AddProductFragment :
    BaseFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSell.setOnClickListener {


            //findNavController().navigate(R.id.action_global_createPostFragment)

            //NavHostFragment.findNavController(this).navigate(R.id.action_global_createPostFragment)

            val action = CreatePostFragmentDirections.actionGlobalCreatePostFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)

        }
    }


}