package com.tbcacademy.shwopapp.base

import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import com.tbcacademy.shwopapp.R
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

fun hideProgressButton(textView: TextView) {

    textView.hideProgress()

}