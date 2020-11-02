package com.shakuro.test

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showAlertDialog(message: String?, retry: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(requireContext()).apply {
        setTitle(R.string.error)
        setMessage(message)
        setPositiveButton(R.string.retry) { _, _ ->
            retry?.invoke()
        }
        setNegativeButton(R.string.no) { _, _ ->
        }
    }
    builder.show()
}