package com.comjeong.nomadworker.ui.common.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.comjeong.nomadworker.ui.common.customview.CustomSpinner

open class BaseActivity<T: ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity() {

    lateinit var binding: T
        private set

    lateinit var spinner: CustomSpinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
    }

    fun showLoadingSpinner(context: Context) {
        spinner = CustomSpinner(context)
        spinner.show()
    }

    fun dismissLoadingSpinner() {
        if (spinner.isShowing) {
            spinner.dismiss()
        }
    }
}