package com.comjeong.nomadworker.data.datasource.local

import com.comjeong.nomadworker.data.datasource.source.settings.SettingsLocalDataSource
import com.comjeong.nomadworker.data.datasource.source.settings.SettingsRemoteDataSource
import com.comjeong.nomadworker.data.model.settings.NoticeLocalData

class SettingsLocalDataSourceImpl : SettingsLocalDataSource {

    override fun fetchNoticeData(): MutableList<NoticeLocalData> {
        return mutableListOf(
            NoticeLocalData(
                "[오픈] 노마드워커를 정식으로 런칭합니다!! :)",
                "2022.09.11",
                "요즘 재택근무 많이 하시나요? 언제 어디서든 눈치보지말고 근무해보세요!\n" +
                        "노마드워커는 여러분 근처에 있는 근무하기 좋은 장소를 추천합니다!\n" +
                        "다른 분들이 추천하는 근무지도 함께 확인해보세요!"
            )
        )
    }
}