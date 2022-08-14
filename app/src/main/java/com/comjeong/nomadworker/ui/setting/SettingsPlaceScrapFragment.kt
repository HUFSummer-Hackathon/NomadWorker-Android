package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSettingsPlaceScrapBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsPlaceScrapFragment :
    BaseFragment<FragmentSettingsPlaceScrapBinding>(R.layout.fragment_settings_place_scrap) {

    private val viewModel: SettingsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.tbPlaceScrap.setNavigationOnClickListener {
            navigateUp()
        }

    }

}