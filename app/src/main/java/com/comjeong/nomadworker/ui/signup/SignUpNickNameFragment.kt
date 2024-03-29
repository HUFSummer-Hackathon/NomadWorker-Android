package com.comjeong.nomadworker.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.databinding.FragmentSignUpNickNameBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setSignUpCloseDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpNickNameFragment :
    BaseFragment<FragmentSignUpNickNameBinding>(R.layout.fragment_sign_up_nick_name) {

    private val viewModel: SignUpViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setNavigation()
        observeDuplicateNickname()
        observeSignUpSuccess()
    }

    private fun observeSignUpSuccess() {
        viewModel.isSignUpSuccess.observe(viewLifecycleOwner, EventObserver<Boolean> { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                navigate(R.id.action_signup_to_on_boarding)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "이미 가입된 사용자입니다.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun observeDuplicateNickname() {
        viewModel.isNicknameDuplicated.observe(viewLifecycleOwner, EventObserver<Boolean> { isDuplicated ->
            if (!isDuplicated) {
                // 가입하기 활성화
                Toast.makeText(requireContext(), R.string.usable_nickname, Toast.LENGTH_SHORT).show()
                viewModel.nickname = binding.etNickname.text.toString().trim()
                handleNextButton(true)
            } else {
                Toast.makeText(requireContext(), R.string.unusable_nickname, Toast.LENGTH_SHORT).show()
                handleNextButton(false)
            }
        })
    }

    private fun setNavigation() {
        binding.tbSignUp.setNavigationOnClickListener {
            navigateUp()
        }

        binding.tbSignUp.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.close -> {
                    setSignUpCloseDialog(requireContext())
                    true
                }
                else -> false
            }
        }
    }

    private fun bindViews() {
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0 && s.length <= Constants.NICKNAME_LENGTH_LIMIT) {
                    binding.tvCheckNickname.visibility = View.VISIBLE
                } else {
                    binding.tvCheckNickname.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.tvCheckNickname.setOnClickListener {
            checkDuplicateNickname()
        }

        binding.btnComplete.setOnClickListener {
            viewModel.signUp()
        }
    }

    private fun checkDuplicateNickname() {
        val nickname = binding.etNickname.text.toString().trim()

        viewModel.checkDuplicateNickname(nickname)
    }

    private fun handleNextButton(canEnable: Boolean) {
        if (canEnable) {
            binding.btnComplete.isEnabled = true
            binding.btnComplete.setBackgroundResource(R.drawable.bg_blue_radius_10)
        } else {
            binding.btnComplete.isEnabled = false
            binding.btnComplete.setBackgroundResource(R.drawable.bg_grey06_radius_10)
        }
    }
}
