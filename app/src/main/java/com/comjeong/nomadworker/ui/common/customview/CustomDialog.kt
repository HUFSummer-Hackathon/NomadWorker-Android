package com.comjeong.nomadworker.ui.common.customview

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.comjeong.nomadworker.databinding.DialogBasicCustomBinding

class CustomDialog(private val context: Context) {

    private val builder = AlertDialog.Builder(context)
    private lateinit var onCLickListener: DialogButtonClickListener

    interface DialogButtonClickListener {
        fun onClicked(event: DialogEvent)
    }

    enum class DialogEvent {
        POSITIVE, NEGATIVE
    }

    fun setDialogButtonClickListener(listener: DialogButtonClickListener) {
        onCLickListener = listener
    }

    fun showDialog(title: String, subtitle: String, subtitleVisibility: Boolean) {
        val inflater = LayoutInflater.from(context)
        val binding = DialogBasicCustomBinding.inflate(inflater, null, false)

        builder.setView(binding.root)
        binding.title = title
        binding.subtitle = subtitle
        binding.subtitleVisibility = subtitleVisibility

        val dialog = builder.create()

        binding.tvPositive.setOnClickListener {
            onCLickListener.onClicked(DialogEvent.POSITIVE)
            dialog.dismiss()
        }

        binding.tvNegative.setOnClickListener {
            onCLickListener.onClicked(DialogEvent.NEGATIVE)
            dialog.dismiss()
        }

        dialog.show()
    }
}