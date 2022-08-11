package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.REPORT_USER_URL
import com.comjeong.nomadworker.databinding.FragmentSettingsReportBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp

class SettingsReportFragment : BaseFragment<FragmentSettingsReportBinding>(R.layout.fragment_settings_report) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bindViews()
    }

    private fun bindViews() {
        binding.wvReport.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            loadUrl(REPORT_USER_URL)
        }

        binding.tbReport.setNavigationOnClickListener {
            navigateUp()
        }
    }
}