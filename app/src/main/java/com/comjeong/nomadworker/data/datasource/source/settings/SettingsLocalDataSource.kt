package com.comjeong.nomadworker.data.datasource.source.settings

import com.comjeong.nomadworker.data.model.settings.NoticeLocalData

interface SettingsLocalDataSource {

    fun fetchNoticeData(): MutableList<NoticeLocalData>

}