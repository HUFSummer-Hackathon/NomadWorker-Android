package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.TOS_URL
import com.comjeong.nomadworker.databinding.FragmentSettingsTosBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp

class SettingsTosFragment : BaseFragment<FragmentSettingsTosBinding>(R.layout.fragment_settings_tos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindViews()
    }

    private fun bindViews() {
        binding.wvTos.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            loadUrl(TOS_URL)
        }

        binding.tbTos.setNavigationOnClickListener {
            navigateUp()
        }
    }
}