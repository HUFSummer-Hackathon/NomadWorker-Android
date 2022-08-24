package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.CONTACT_ONE_BY_ONE_URL
import com.comjeong.nomadworker.databinding.FragmentSettingsContactBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp

class SettingsContactFragment : BaseFragment<FragmentSettingsContactBinding>(R.layout.fragment_settings_contact) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindViews()
    }

    private fun bindViews() {
        binding.wvContact.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            loadUrl(CONTACT_ONE_BY_ONE_URL)
        }

        binding.tbContact.setNavigationOnClickListener {
            navigateUp()
        }
    }
}