package com.comjeong.nomadworker.ui.common.extension

import androidx.lifecycle.MutableLiveData

fun <T: Any?> MutableLiveData<T>.default(initialValue: T) = this.apply { value = initialValue }