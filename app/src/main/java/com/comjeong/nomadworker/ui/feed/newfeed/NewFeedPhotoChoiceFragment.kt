package com.comjeong.nomadworker.ui.feed.newfeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.OPEN_GALLERY
import com.comjeong.nomadworker.databinding.FragmentNewFeedPhotoChoiceBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setNewFeedCloseDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.permission.UserPermission.isGrantedPhotoGalleryPermission
import com.comjeong.nomadworker.ui.permission.UserPermission.requestPhotoGalleryPermission
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


class NewFeedPhotoChoiceFragment : BaseFragment<FragmentNewFeedPhotoChoiceBinding>(R.layout.fragment_new_feed_photo_choice){

    lateinit var file : File
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindCancelFeedPosting()
    }

    private fun bindViews(){
        binding.btnDoneToSelectPhoto.setOnClickListener {
            navigate(R.id.action_navigation_photo_choice_to_navigation_content_choice)
        }

        binding.btnCamera.setOnClickListener {
            if(requireActivity().isGrantedPhotoGalleryPermission()){
                Timber.d("갤러리 진입 전")
                openGallery()
            }
            else{
                requireActivity().requestPhotoGalleryPermission()
            }
        }
    }

    private fun bindCancelFeedPosting(){
        binding.tbNewFeedTopBanner.setNavigationOnClickListener {
            setNewFeedCloseDialog(requireActivity())
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY){
                val currentImageUrl: Uri? = data?.data
                if(currentImageUrl != null){
                    file = toFile(requireActivity(),currentImageUrl)
                    NewFeedInfo.image = getImageMultipart("file",file)
                }
                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, currentImageUrl)
                    binding.imgSelectedImage.setImageBitmap(bitmap)
                    handleNextButton(true)
                }catch (e: Exception){
                    Timber.d("FILE FAILED $e")
                }
            }
        }
    }



    private fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    private fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(2 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
    }

    private fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = createTempFile(context, fileName)
        copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }

    private fun getImageMultipart(key: String, file: File): MultipartBody.Part {
        val multipartBody = MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaType())
        )
        return multipartBody
    }



    private fun handleNextButton(canEnable: Boolean) {
        if (canEnable) {
            binding.btnDoneToSelectPhoto.isEnabled = true
            binding.btnDoneToSelectPhoto.setBackgroundResource(R.drawable.bg_blue_radius_10)
        } else {
            binding.btnDoneToSelectPhoto.isEnabled = false
            binding.btnDoneToSelectPhoto.setBackgroundResource(R.drawable.bg_grey06_radius_10)
        }
    }
}