package com.comjeong.nomadworker.data.model.settings

data class NoticeLocalData(
    val title: String,
    val date: String,
    val content: String,
    var isVisible: Boolean = false
)
