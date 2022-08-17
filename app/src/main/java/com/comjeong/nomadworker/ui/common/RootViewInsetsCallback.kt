package com.comjeong.nomadworker.ui.common

import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat

class RootViewInsetsCallback(private val insetTypes: Int, private val insetTypes2: Int) : OnApplyWindowInsetsListener {
    // 내부의 정책에 따라 뷰가 WindowInsets을 적용해야 할 때 호출됨
    override fun onApplyWindowInsets(v: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
        val types = insetTypes or insetTypes2

        // 패딩으로 설정하여, 결정된 insets을 적용
        val typeInsets = windowInsets.getInsets(types)
        v.setPadding(typeInsets.left, typeInsets.top, typeInsets.right, typeInsets.bottom)

        // 새로운 WindowInsetsCompat.CONSUMED를 반환하여 insets가 뷰 계층 구조로 더 이상 전달되지 않도록 함
        // 이것은 deprecated된 WindowInsetsCompat.consumeSystemWindowInsets() 및 관련 함수를 대체합니다
        return WindowInsetsCompat.CONSUMED
    }
}