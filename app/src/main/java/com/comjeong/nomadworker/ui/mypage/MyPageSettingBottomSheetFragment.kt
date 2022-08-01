package com.comjeong.nomadworker.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comjeong.nomadworker.databinding.FragmentMyPageSettingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyPageSettingBottomSheetFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMyPageSettingBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageSettingBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUserProfileModification.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }

        binding.btnUserLogout.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }

    companion object {
        fun getNewInstance(
            itemClick: (Int) -> Unit
        ): MyPageSettingBottomSheetFragment {
            return MyPageSettingBottomSheetFragment(itemClick)
        }
    }
}