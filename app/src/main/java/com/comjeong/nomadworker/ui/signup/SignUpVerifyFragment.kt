package com.comjeong.nomadworker.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.VERIFICATION_CODE
import com.comjeong.nomadworker.common.Constants.TYPED_USER_EMAIL
import com.comjeong.nomadworker.databinding.FragmentSignUpVerifyBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setSignUpCloseDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp

class SignUpVerifyFragment : BaseFragment<FragmentSignUpVerifyBinding>(R.layout.fragment_sign_up_verify) {

    private var verificationCode: String? = ""
    private var userEmail: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificationCode = requireArguments().getString(VERIFICATION_CODE)
        userEmail = requireArguments().getString(TYPED_USER_EMAIL).toString()

        setNavigation()
        bindViews()
    }

    private fun setNavigation() {
        binding.btnNext.setOnClickListener {
            navigate(R.id.action_verify_to_password)
        }

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

        binding.tvShowTypedUserEmail.text = userEmail
    }

    private fun bindViews() {
        binding.etVerifyCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isVerified()) {
                    handleNextButton(true)
                    moveNextStep()
                } else {
                    handleNextButton(false)

                }
            }
        })
    }

    private fun isVerified(): Boolean {
        val inputVerificationCode = binding.etVerifyCode.text.toString().trim()
        return if (inputVerificationCode == verificationCode) {
            binding.etVerifyCode.setBackgroundResource(R.drawable.bg_green_radius_10)
            binding.tvStatusMessage.visibility = View.VISIBLE
            binding.tvStatusMessage.text = getString(R.string.verify_email_success)
            binding.tvStatusMessage.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.nomad_positive_green
                )
            )
            true
        } else {
            binding.etVerifyCode.setBackgroundResource(R.drawable.bg_red_radius_10)
            binding.tvStatusMessage.visibility = View.VISIBLE
            binding.tvStatusMessage.text = getString(R.string.verify_email_fail)
            binding.tvStatusMessage.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.nomad_accent_red
                )
            )
            false
        }
    }

    private fun handleNextButton(canEnable: Boolean) {
        if (canEnable) {
            binding.btnNext.isEnabled = true
            binding.btnNext.setBackgroundResource(R.drawable.bg_blue_radius_10)
        } else {
            binding.btnNext.isEnabled = false
            binding.btnNext.setBackgroundResource(R.drawable.bg_grey06_radius_10)
        }
    }

    private fun moveNextStep() {
        binding.btnNext.setOnClickListener {
            navigate(R.id.action_verify_to_password)
        }
    }
}