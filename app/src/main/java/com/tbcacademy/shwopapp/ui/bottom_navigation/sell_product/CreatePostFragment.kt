package com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.base.BaseFragment
import com.tbcacademy.shwopapp.base.snackbar
import com.tbcacademy.shwopapp.databinding.CreatePostFragmentBinding
import com.tbcacademy.shwopapp.utils.EventObserver
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostFragment :
    BaseFragment<CreatePostFragmentBinding>(CreatePostFragmentBinding::inflate) {


    @Inject
    lateinit var glide: RequestManager

    private val viewModel: CreatePostViewModel by viewModels()

    private lateinit var cropContent: ActivityResultLauncher<Any?>

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 9)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(requireContext())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    private var curImageUri: Uri? = null
    private var secondImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cropContent = registerForActivityResult(cropActivityResultContract) {
            it?.let {
                viewModel.setCurImageUri(it)
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCamera.setOnClickListener {
            pickImage()
        }
        binding.ivSelect.setOnClickListener {
            cropContent.launch(null)
        }
        binding.secondImage.setOnClickListener {
            cropContent.launch(null)
        }
        binding.btnPublish.setOnClickListener {
            curImageUri?.let { uri ->
                viewModel.createPost(
                    uri,
                    uri,
                    uri,
                    uri,
                    binding.etDecription.text.toString(),
                    binding.etPrice.text.toString(),
                    binding.etProduct.text.toString()
                )
            } ?: snackbar(getString(R.string.error_no_image_chosen))
        }


        observer()
    }

    private fun pickImage() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED ) {
            cropContent.launch(null)

           /* val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,222)*/
        }else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),112)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 112) {
           cropContent.launch(null)
        }else {
            snackbar("Ooops, you just denied the permission for storage. You can also allow it from the settings")
        }

    }

    private fun observer() {

        viewModel.curImageUri.observe(viewLifecycleOwner) {
            curImageUri = it
            binding.ivCamera.isVisible = false
            glide.load(curImageUri).into(binding.ivSelect)

        }

        viewModel.createPostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                snackbar(it)

            },
            onLoading = {
                binding.progressBar.visibility = View.VISIBLE

            }
        ) {
            binding.progressBar.visibility = View.INVISIBLE
            findNavController().popBackStack()

        })
    }


}