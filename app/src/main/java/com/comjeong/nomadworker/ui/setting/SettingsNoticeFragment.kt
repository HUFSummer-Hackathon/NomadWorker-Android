package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.data.datasource.local.SettingsLocalDataSourceImpl
import com.comjeong.nomadworker.databinding.FragmentSettingsNoticeBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import timber.log.Timber

class SettingsNoticeFragment :
    BaseFragment<FragmentSettingsNoticeBinding>(R.layout.fragment_settings_notice) {

    private lateinit var noticeAdapter: NoticeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setRecyclerView()
    }

    private fun bindViews() {
        binding.tbNotice.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun setRecyclerView() {
        val noticeData = SettingsLocalDataSourceImpl().fetchNoticeData()
        Timber.d("$noticeData")

        noticeAdapter = NoticeAdapter()
        binding.rvNotice.adapter = noticeAdapter
        noticeAdapter.setNoticeData(noticeData)
    }
}