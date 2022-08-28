package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.PRIVACY_POLICY_URL
import com.comjeong.nomadworker.databinding.FragmentSettingsPrivacyBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp

class SettingsPrivacyFragment : BaseFragment<FragmentSettingsPrivacyBinding>(R.layout.fragment_settings_privacy) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindViews()
    }

    private fun bindViews() {
        binding.wvPrivacy.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            loadUrl(PRIVACY_POLICY_URL)
        }

        binding.tbPrivacy.setNavigationOnClickListener {
            navigateUp()
        }
    }
}