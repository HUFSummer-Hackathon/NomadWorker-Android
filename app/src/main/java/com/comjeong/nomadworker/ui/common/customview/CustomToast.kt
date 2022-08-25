package com.comjeong.nomadworker.ui.common.customview

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.ToastErrorBinding
import com.comjeong.nomadworker.databinding.ToastStandardBinding
import com.comjeong.nomadworker.databinding.ToastWarningBinding

object CustomToast {

    lateinit var toast: Toast

    enum class ToastType {
        STANDARD, WARNING, ERROR
    }

    fun makeToast(context: Context, message: String, type: ToastType): Toast {
        when (type) {
            ToastType.STANDARD -> {
                toast =  Toast(context).also {
                    val view = LayoutInflater.from(context).inflate(R.layout.toast_warning, null)

                    val messageTextView = view.findViewById<AppCompatTextView>(R.id.tv_standard_message)
                    messageTextView.text = message

                    it.view = view
                    it.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
                    it.duration = Toast.LENGTH_SHORT
                }
            }
            ToastType.WARNING -> {
                toast =  Toast(context).also {
                    val view = LayoutInflater.from(context).inflate(R.layout.toast_warning, null)

                    val messageTextView = view.findViewById<AppCompatTextView>(R.id.tv_warning_message)
                    messageTextView.text = message

                    it.view = view
                    it.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
                    it.duration = Toast.LENGTH_SHORT
                }
            }

            ToastType.ERROR -> {
                toast =  Toast(context).also {
                    val view = LayoutInflater.from(context).inflate(R.layout.toast_error, null)

                    val messageTextView = view.findViewById<AppCompatTextView>(R.id.tv_error_message)
                    messageTextView.text = message

                    it.view = view
                    it.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
                    it.duration = Toast.LENGTH_SHORT
                }
            }
        }
        return toast
    }
}


private fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
