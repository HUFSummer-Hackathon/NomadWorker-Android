package com.comjeong.nomadworker.ui.feed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentNewFeedContentChoiceBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.DialogUtil.setNewFeedCloseDialog
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NewFeedContentChoiceFragment : BaseFragment<FragmentNewFeedContentChoiceBinding>(R.layout.fragment_new_feed_content_choice) {

    private val viewModel : FeedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindEditText()
        bindCancelFeedPosting()
    }

    private fun bindViews(){
        binding.btnDoneToWriteContent.setOnClickListener {
            saveText()
            navigate(R.id.action_navigation_content_choice_to_navigation_place_choice)
        }

        binding.tbNewFeedTopBanner.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun bindCancelFeedPosting(){
        binding.tbNewFeedTopBanner.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.close -> {
                    setNewFeedCloseDialog(requireContext())
                    true
                }
                else -> false
            }
        }
    }

    private fun bindEditText(){
        binding.tvContentInputContainer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isValidatePattern()) {
                    handleNextButton(true)
                } else {
                    handleNextButton(false)
                }
            }
        })
    }

    private fun isValidatePattern() : Boolean {
        val text = binding.tvContentInputContainer.text
        return !text.isNullOrEmpty()
    }

    private fun saveText() {
        val text = binding.tvContentInputContainer.text.toString()
        viewModel.content = text
    }

    private fun handleNextButton(canEnable: Boolean) {
        if (canEnable) {
            binding.btnDoneToWriteContent.isEnabled = true
            binding.btnDoneToWriteContent.setBackgroundResource(R.drawable.bg_blue_radius_10)
        } else {
            binding.btnDoneToWriteContent.isEnabled = false
            binding.btnDoneToWriteContent.setBackgroundResource(R.drawable.bg_grey06_radius_10)
        }
    }
}