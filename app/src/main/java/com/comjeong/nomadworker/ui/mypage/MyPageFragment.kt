package com.comjeong.nomadworker.ui.mypage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.comjeong.nomadworker.NomadWorkerApplication.Companion.getAppContext
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_ID_KEY
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.FragmentMyPageBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateWithBundle
import com.comjeong.nomadworker.ui.permission.UserPermission.isGrantedPhotoGalleryPermission
import com.comjeong.nomadworker.ui.permission.UserPermission.requestPhotoGalleryPermission
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel: MyPageViewModel by sharedViewModel()
    private var userId: Long = 0

    lateinit var currentImagePath: String
    lateinit var file: File


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userId = NomadSharedPreferences.getUserId()
        NomadSharedPreferences.loadUserInfo()
        viewModel.userId = userId

        viewModel.getUserTotalFeedsWithInfo()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserFeed()
        observeUserInfo()
        observeFeedDetailEvent()

//        binding.btnMypageSetting.setOnClickListener {
//            showBottomSheetDialog()
//        }

        binding.btnMypageSetting.setOnClickListener {
            navigate(R.id.action_my_page_to_settings)
        }
    }

    private fun observeUserInfo() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            binding.userInfo = userInfo
        }
    }

    private fun observeFeedDetailEvent() {
        viewModel.openFeedDetailEvent.observe(viewLifecycleOwner, EventObserver<Long> { feedId ->
            moveFeedDetail(feedId)
        })
    }

    private fun moveFeedDetail(feedId: Long) {
        navigateWithBundle(
            R.id.action_my_page_to_feed_detail, bundleOf(
                FEED_ID_KEY to feedId
            )
        )
    }

    private fun setUserFeed() {
        binding.rvUserFeed.adapter = UserFeedAdapter(viewModel).apply {
            viewModel.userFeedList.observe(viewLifecycleOwner) { feedList ->
                submitList(feedList)
            }
        }
    }

//    private fun showBottomSheetDialog() {
//        val settingsFragment = MyPageSettingBottomSheetFragment.getNewInstance { clickId ->
//            when (clickId) {
//                0 -> {
//                    if (requireActivity().isGrantedPhotoGalleryPermission()) {
//                        openGallery()
//                    } else {
//                        requireActivity().requestPhotoGalleryPermission()
//                    }
//                }
//                1 -> {
//                    NomadSharedPreferences.logoutUser()
//                    Toast.makeText(requireContext(), "로그아웃 되었습니다 :)", Toast.LENGTH_SHORT).show()
//                    requireActivity().finish()
//                }
//            }
//        }
//
//        settingsFragment.show(childFragmentManager, settingsFragment.tag)
//    }

//    private fun openGallery() {
//        val intent = Intent().apply {
//            action = Intent.ACTION_GET_CONTENT
//            setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*"
//            )
//        }
//        singleImageResult.launch(intent)
//    }

    private fun convertUriToFile(context: Context, uri: Uri): File {
        val file = createImageFile(context, uri)
        copyUriToFile(uri, file)

        return File(file.absolutePath)
    }

    // **
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

    private fun setBitmapFromUriByVersion(uri: Uri) {
        lateinit var bitmap: Bitmap

        if (Build.VERSION.SDK_INT >= 29) {
            val source: ImageDecoder.Source =
                ImageDecoder.createSource(getAppContext().contentResolver, uri)

            try {
                bitmap = ImageDecoder.decodeBitmap(source)
                binding.ivUserProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getAppContext().contentResolver, uri)
                binding.ivUserProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}