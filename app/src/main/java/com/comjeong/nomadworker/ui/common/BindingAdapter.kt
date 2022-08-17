package com.comjeong.nomadworker.ui.common

import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.GlideApp
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import java.text.DecimalFormat

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: AppCompatImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()){
            GlideApp.with(view)
                .load(imageUrl)
                .into(view)
        } else {
            GlideApp.with(view)
                .load(R.drawable.bg_nothing_to_show)
                .into(view)
        }

    }

    @JvmStatic
    @BindingAdapter("nickname")
    fun setNickname(view: AppCompatTextView, nickname: String) {
        view.text = nickname +"님, 오늘은 어디서 근무하시나요?"
    }

    @JvmStatic
    @BindingAdapter("priceAmount")
    fun setPriceAmount(view: AppCompatTextView, price: Any) {
        val decimalFormat = DecimalFormat("#,###")
        view.text = view.context.getString(R.string.unit_kr_currency, decimalFormat.format(price))
    }

    @JvmStatic
    @BindingAdapter("toString")
    fun setToString(view: AppCompatTextView, likeCount: Int) {
        view.text = likeCount.toString()
    }

    @JvmStatic
    @BindingAdapter("bindLikeStatus")
    fun setLikeStatus(view: ImageButton, favorite: Boolean) {
        var select = true
        view.isSelected = favorite
    }

    @JvmStatic
    @BindingAdapter("visibility", "subtitle")
    fun setSubtitleVisibility(View: AppCompatTextView, visibility: Boolean, subtitle: String) {
        when (visibility) {
            true -> {
                View.visibility = android.view.View.VISIBLE
                View.text = subtitle
            }
            false -> {
                View.visibility = android.view.View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun setTime(view: AppCompatTextView, timeToString: String) {
        val idx = 10
        view.text = timeToString.replace("T", " ")
    }

    @JvmStatic
    @BindingAdapter("optionVisibility")
    fun setOptionVisibility(view: AppCompatImageButton, userId: Long) {
        when(userId == NomadSharedPreferences.getUserId()){
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.INVISIBLE
        }
    }

//    @JvmStatic
//    @BindingAdapter("menuVisibility")
//    fun setMenuVisibility(view: ConstraintLayout, model: List<PlaceDetailResult.Result.Menu>?) {
//        if (model.isNullOrEmpty()) {
//            setVisibility(view, false)
//        }
//        setVisibility(view, true)
//    }
//
//    @JvmStatic
//    @BindingAdapter("rentalPriceVisibility")
//    fun setRentalPriceVisibility(view: ConstraintLayout, price: String?) {
//        if (price.isNullOrEmpty()) {
//            setVisibility(view, false)
//        }
//        setVisibility(view, true)
//    }
//
//    @JvmStatic
//    @BindingAdapter("isVisible")
//    fun setVisibility(view: ConstraintLayout, isVisible: Boolean) {
//        view.visibility = if (isVisible) View.VISIBLE else View.GONE
//    }
}