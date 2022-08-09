package com.comjeong.nomadworker.data.datasource.source.settings

import com.comjeong.nomadworker.data.model.settings.NoticeLocalData

interface SettingsDataSource {

    fun fetchNoticeData(): MutableList<NoticeLocalData>
}