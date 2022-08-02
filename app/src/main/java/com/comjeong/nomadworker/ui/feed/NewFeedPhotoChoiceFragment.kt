package com.comjeong.nomadworker.ui.feed

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.OPEN_GALLERY
import com.comjeong.nomadworker.databinding.FragmentNewFeedPhotoChoiceBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.DialogUtil.setNewFeedCloseDialog
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewFeedPhotoChoiceFragment : BaseFragment<FragmentNewFeedPhotoChoiceBinding>(R.layout.fragment_new_feed_photo_choice){

    private val viewModel : FeedViewModel by viewModel()
    
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
            openGallery()
        }
    }

    private fun bindCancelFeedPosting(){
        binding.tbNewFeedTopBanner.setNavigationOnClickListener {
            setNewFeedCloseDialog(requireActivity())
        }
    }

    private fun openGallery(){
        binding.btnCamera.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, OPEN_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY){
                val currentImageUrl: Uri? = data?.data
                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, currentImageUrl)
                    binding.imgSelectedImage.setImageBitmap(bitmap)
                    changeToMultipart(bitmap)

                    setImageOnView(currentImageUrl)
                    handleNextButton(true)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setImageOnView(imageUri : Uri?) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.imgSelectedImage)
    }

    private fun changeToMultipart(bitmap: Bitmap){
        val bitmapRequestBody = NewBitmapRequestBody(bitmap)
        val bitmapMultipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("feed_image", ".png", bitmapRequestBody)
            viewModel.image = bitmapMultipartBody
    }

    inner class NewBitmapRequestBody(private val bitmap: Bitmap): RequestBody(){
        override fun contentType(): MediaType = "image/jpeg".toMediaType()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
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