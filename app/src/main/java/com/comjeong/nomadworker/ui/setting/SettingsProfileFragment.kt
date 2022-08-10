package com.comjeong.nomadworker.ui.setting

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSettingsProfileBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsProfileFragment : BaseFragment<FragmentSettingsProfileBinding>(R.layout.fragment_settings_profile) {

    private val viewModel: SettingsViewModel by viewModel()

//    private val singleImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val imageUri = result.data?.data
//                Timber.d("$imageUri")
//                imageUri?.let { uri ->
//                    setBitmapFromUriByVersion(uri)
//
//                    file = convertUriToFile(requireContext(), imageUri)
//                    Timber.d("$file")
//
//                    viewModel.profileImage = convertFileToMultiPart(file)
//
//                    viewModel.updateUserProfileImage()
//                }
//            }
//        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

    }

    private fun bindViews() {
        binding.tbProfile.setNavigationOnClickListener {
            navigateUp()
        }
    }
}