package com.comjeong.nomadworker.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.data.model.settings.NoticeLocalData
import com.comjeong.nomadworker.databinding.ItemSettingsNoticeBinding
import timber.log.Timber

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.SettingNoticeViewHolder>() {
    var settingNotice : MutableList<NoticeLocalData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingNoticeViewHolder {
        val binding = ItemSettingsNoticeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingNoticeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingNoticeViewHolder,
        position: Int
    ) {
        holder.onBind(settingNotice[position])
    }

    override fun getItemCount(): Int {
        Timber.d("settingNotice ${settingNotice.size}")
        return settingNotice.size
    }


    class SettingNoticeViewHolder(
        val binding : ItemSettingsNoticeBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(settingNotice : NoticeLocalData){
            binding.apply {
                notice = settingNotice
                executePendingBindings()

                clNoticeContainer.setOnClickListener {
                    if (clExpandedNoticeContent.visibility == View.VISIBLE) {
                        clExpandedNoticeContent.visibility = View.GONE
                    } else if (clExpandedNoticeContent.visibility == View.GONE) {
                        clExpandedNoticeContent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun setNoticeData(settingNotice : MutableList<NoticeLocalData>){
        this.settingNotice = settingNotice
        notifyDataSetChanged()
    }
}