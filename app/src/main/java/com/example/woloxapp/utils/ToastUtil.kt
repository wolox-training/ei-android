package com.example.woloxapp.utils

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.widget.Toast

class ToastUtil {
    fun showToast(string: Any, context: Context) {
        val toastHorizontal = 0
        val toastVertical = 20
        val titleString = when (string) {
            is String -> string
            is Int -> Resources.getSystem().getString(string)
            else -> throw IllegalAccessError()
        }
        val toast = Toast.makeText(context, titleString, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, toastHorizontal, toastVertical)
        toast.show()
    }
}
