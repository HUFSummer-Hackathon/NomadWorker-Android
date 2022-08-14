package com.comjeong.nomadworker.ui.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.comjeong.nomadworker.NomadWorkerApplication
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.FragmentSettingsProfileBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SettingsProfileFragment : BaseFragment<FragmentSettingsProfileBinding>(R.layout.fragment_settings_profile) {

    private val viewModel: SettingsViewModel by sharedViewModel()

    lateinit var file: File

    private val singleImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                Timber.d("$imageUri")
                imageUri?.let { uri ->
                    setBitmapFromUriByVersion(uri)

                    file = convertUriToFile(requireContext(), imageUri)
                    Timber.d("$file")

                    viewModel.profileImage = convertFileToMultiPart(file)

                    viewModel.updateUserProfileImage()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()

    }

    private fun bindViews() {
        val userProfileImage = NomadSharedPreferences.getUserProfileImage()

        binding.profileImage = userProfileImage
        binding.tbProfile.setNavigationOnClickListener {
            navigateUp()
        }

        // 이미지 업데이트
        binding.tvUpdateProfile.setOnClickListener {
            openGallery()
        }
    }

    private fun setBitmapFromUriByVersion(uri: Uri) {
        lateinit var bitmap: Bitmap

        if (Build.VERSION.SDK_INT >= 29) {
            val source: ImageDecoder.Source =
                ImageDecoder.createSource(NomadWorkerApplication.getAppContext().contentResolver, uri)

            try {
                bitmap = ImageDecoder.decodeBitmap(source)
                binding.ivProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(NomadWorkerApplication.getAppContext().contentResolver, uri)
                binding.ivProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
        }
        singleImageResult.launch(intent)
    }

    private fun convertUriToFile(context: Context, uri: Uri): File {
        val file = createImageFile(context, uri)
        copyUriToFile(uri, file)

        return File(file.absolutePath)
    }

    private fun createImageFile(context: Context, uri: Uri): File {
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        val fileName = uri.toString().split("/").last()
        val suffix = context.contentResolver.getType(uri)?.split("/")?.last()
        val storageDirectory: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File(storageDirectory, "${fileName}.${suffix}")
    }

    private fun copyUriToFile(uri: Uri, file: File) {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(2 * 1024)
        while (true) {
            val byteCount = inputStream?.read(buffer)
            if (byteCount != null) {
                if (byteCount < 0) break

                outputStream.write(buffer, 0, byteCount)
            }
        }

        outputStream.flush()
    }

    private fun convertFileToMultiPart(file: File): MultipartBody.Part {
        val fileRequestBody = file.asRequestBody("image/*".toMediaType())

        return MultipartBody.Part.createFormData(
            "image",
            file.name,
            fileRequestBody
        )
    }
}