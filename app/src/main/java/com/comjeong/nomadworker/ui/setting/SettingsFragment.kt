package com.comjeong.nomadworker.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.comjeong.nomadworker.BuildConfig
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.FragmentSettingsBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.CustomDialog
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.signin.SignInActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {

        with(binding) {
            tvUserNickname.text = NomadSharedPreferences.getUserNickName()
            tvVersionInfo.text = "v ${getVersionName()}"

            // 프로필 이미지 수정
            tvUpdateProfileImage.setOnClickListener {

            }

            // 스크랩한 장소 보기
            tvScrap.setOnClickListener {

            }

            // 공지사항 보기
            tvNotice.setOnClickListener {
                navigate(R.id.action_settings_to_notice)
            }

            // 1:1 문의
            tvContactOneByOne.setOnClickListener {
                navigate(R.id.action_settings_to_contact)
            }

            // 사용자 신고하기
            tvReportUser.setOnClickListener {
                navigate(R.id.action_settings_to_report)
            }

            // 서비스 이용 약관
            tvTos.setOnClickListener {
                navigate(R.id.action_settings_to_tos)
            }

            // 개인정보 처리 방침
            tvPersonalPrivacy.setOnClickListener {
                navigate(R.id.action_settings_to_privacy)
            }

            // 오픈소스 라이선스
            tvOssLicense.setOnClickListener {
                startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
                OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
            }

            // 로그아웃
            tvLogout.setOnClickListener {
                val dialog = CustomDialog(requireActivity())
                dialog.showDialog("로그아웃 하시겠습니까?", "그동안의 정보들은 연동된 계정에 안전하게 보관됩니다 :)", true)
                dialog.setDialogButtonClickListener(object :
                    CustomDialog.DialogButtonClickListener {
                    override fun onClicked(event: CustomDialog.DialogEvent) {
                        when (event) {
                            // 로그아웃 처리
                            CustomDialog.DialogEvent.POSITIVE -> {
                                NomadSharedPreferences.logoutUser()
                                Toast.makeText(requireActivity(), "로그아웃 되었습니다", Toast.LENGTH_SHORT)
                                    .show()
                                ActivityCompat.finishAffinity(requireActivity())
                                startActivity(Intent(requireActivity(), SignInActivity::class.java))
                            }

                            else -> Unit
                        }
                    }
                })
            }
        }
    }

    private fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }
}