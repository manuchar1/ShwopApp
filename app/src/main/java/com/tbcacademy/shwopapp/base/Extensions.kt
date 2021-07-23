package com.tbcacademy.shwopapp.base

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.main.MainActivity
import com.tbcacademy.shwopapp.ui.bottom_navigation.NavHomeFragment
import com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product.CreatePostFragment
import com.tbcacademy.shwopapp.utils.Resource


fun Fragment.snackbar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
}

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {

    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error occured")
    }
}

fun Fragment.showProgressButton(textView: TextView) {

    bindProgressButton(textView)
    textView.attachTextChangeAnimator()

    textView.showProgress {
        buttonTextRes = R.string.loading
        progressColor = Color.WHITE

    }
}

fun Fragment.showDialog(@StringRes title: Int, message: String) {
    context?.let {
        AlertDialog.Builder(it)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.common_Ok) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()


    }

}















