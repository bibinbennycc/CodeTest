package com.codetest.feature.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.codetest.R
import dagger.android.support.DaggerDialogFragment

abstract class BaseDialogFragment : DaggerDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialogTheme
    }

    fun setupToolbarBackButton(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { dismiss() }
    }

    fun showError(message: String?) {
        AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.error_title))
                .setMessage(message ?: getString(R.string.message_something_went_wrong))
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
                .create()
                .show()
    }

    abstract fun getLayoutId(): Int
}