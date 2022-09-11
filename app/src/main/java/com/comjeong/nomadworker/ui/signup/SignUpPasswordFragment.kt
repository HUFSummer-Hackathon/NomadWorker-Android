package com.comjeong.nomadworker.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSignUpPasswordBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setSignUpCloseDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern

class SignUpPasswordFragment :
    BaseFragment<FragmentSignUpPasswordBinding>(R.layout.fragment_sign_up_password) {

    private val viewModel: SignUpViewModel by sharedViewModel()
    private var isChangedPassword: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
        bindViews()
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
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isChangedPassword = s?.length!! > 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.isEmpty()!!){
                    binding.etPassword.setBackgroundResource(R.drawable.bg_white_radius_10_stroke_bluegrey)
                    binding.tvPasswordStatusMessage.visibility = View.INVISIBLE
                }
                else{
                    checkValidationPattern()
                    activateNextButtonWithPasswordVerification()
                }

            }
        })

        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.isEmpty()!!){
                    binding.etConfirmPassword.setBackgroundResource(R.drawable.bg_white_radius_10_stroke_bluegrey)
                    binding.tvConfirmPasswordStatusMessage.visibility = View.INVISIBLE
                }
                else{
                    activateNextButtonWithPasswordVerification()
                }
            }
        })
    }

    /**
     * 숫자, 문자, 특수문자 포함 8~15자 제한
     */
    private fun checkValidationPattern() {
        val password = binding.etPassword.text.toString()
        val validationPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$"
        val isValidated = if(password.indexOf(" ") == -1) Pattern.matches(validationPattern, password) else false

        return if (isValidated) {
            binding.tvPasswordStatusMessage.visibility = View.VISIBLE
            binding.tvPasswordStatusMessage.text = getString(R.string.correct_password)
            binding.tvPasswordStatusMessage.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.nomad_positive_green
                )
            )
        } else {
            binding.tvPasswordStatusMessage.visibility = View.VISIBLE
            binding.tvPasswordStatusMessage.text = getString(R.string.incorrect_password)
            binding.tvPasswordStatusMessage.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.nomad_accent_red
                )
            )
        }
    }

    private fun isSamePassword(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        return if (password == confirmPassword) {
            binding.tvConfirmPasswordStatusMessage.visibility = View.VISIBLE
            binding.tvConfirmPasswordStatusMessage.text = getString(R.string.same_password)
            binding.tvConfirmPasswordStatusMessage.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.nomad_positive_green
                )
            )
            true
        } else {
            binding.tvConfirmPasswordStatusMessage.visibility = View.VISIBLE
            binding.tvConfirmPasswordStatusMessage.text = getString(R.string.different_password)
            binding.tvConfirmPasswordStatusMessage.setTextColor(
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

    private fun activateNextButtonWithPasswordVerification() {
        if (isSamePassword()) {
            handleNextButton(true)
            moveNextStep()
        } else {
            handleNextButton(false)
        }
    }

    private fun moveNextStep() {
        binding.btnNext.setOnClickListener {
            navigate(R.id.action_password_to_nickname)

            viewModel.password = binding.etConfirmPassword.text.toString().trim()
        }
    }


}