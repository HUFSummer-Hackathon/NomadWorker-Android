package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSettingsBinding
import com.comjeong.nomadworker.ui.common.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            // 프로필 이미지 수정
            tvUpdateProfileImage.setOnClickListener {

            }

            // 스크랩한 장소 보기
            tvScrap.setOnClickListener {

            }

            // 공지사항 보기
            tvNotice.setOnClickListener {

            }

            // 1:1 문의
            tvContactOneByOne.setOnClickListener {

            }

            // 사용자 신고하기
            tvReportUser.setOnClickListener {

            }

            // 서비스 이용 약관
            tvTos.setOnClickListener {

            }

            // 개인정보 처리 방침
            tvPersonalPrivacy.setOnClickListener {

            }

            // 오픈소스 라이선스
            tvOssLicense.setOnClickListener {

            }

            // 로그아웃
            tvLogout.setOnClickListener {

            }
        }
    }
}