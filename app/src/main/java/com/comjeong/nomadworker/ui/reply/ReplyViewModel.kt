package com.comjeong.nomadworker.ui.reply

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.data.model.reply.DeleteReplyRequestData
import com.comjeong.nomadworker.data.model.reply.PostReplyRequestData
import com.comjeong.nomadworker.domain.model.reply.Author
import com.comjeong.nomadworker.domain.model.reply.GetReplyResult
import com.comjeong.nomadworker.domain.model.reply.PostReplyResult
import com.comjeong.nomadworker.domain.repository.reply.ReplyRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ReplyViewModel(private val repository: ReplyRepository): ViewModel() {

    private var _replyId: Long = -1
    var replyId: Long = _replyId
        set(value) {
            _replyId = value
            field = value
        }

    private var _feedId: Long = -1
    var feedId: Long = _feedId
        set(value) {
            _feedId = value
            field = value
        }

    private var _replyContent: String = ""
    var replyContent: String = _replyContent
        set(value) {
            _replyContent = value
            field = value
        }

    private var _placeName: String = ""
    var placeName: String = _placeName
        set(value) {
            _placeName = value
            field = value
        }

    private val _replyList: MutableLiveData<List<GetReplyResult.Result.Other>> = MutableLiveData<List<GetReplyResult.Result.Other>>()
    val replyList: MutableLiveData<List<GetReplyResult.Result.Other>> = _replyList

    private val _authorList: MutableLiveData<List<Author>> = MutableLiveData<List<Author>>()
    val authorList: MutableLiveData<List<Author>> = _authorList

    private val _isPostReply: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val isPostReply: MutableLiveData<Event<Boolean>> = _isPostReply

    private val _isClickOption: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val isClickOption: MutableLiveData<Event<Boolean>> = _isClickOption

    private val _isDeleteReply: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val isDeleteReply: MutableLiveData<Event<Boolean>> = _isDeleteReply

    fun getReply() {
        viewModelScope.launch {
            try {
                val response = repository.getReply(_feedId)

                when (response.status) {
                    200 -> {
                        if(response.data.reply != null) _replyList.value = response.data.reply
                        else _replyList.value = emptyList()

                        _authorList.value = listOf(Author(response.data.userNickname, response.data.userImage, response.data.placeName, response.data.feedContent))
                    }
                    400 -> {
                        _replyList.value = emptyList()
                        _authorList.value = emptyList()
                    }
                }
                Timber.d("SUCCESS : $response")
            } catch (e: Throwable) {
                Timber.d("FAILED : $e")
            }
        }
    }

    fun postReply(postTime: String) {
        viewModelScope.launch {
            try {
                val response = repository.postReply(PostReplyRequestData(_feedId, NomadSharedPreferences.getUserId(), _replyContent, postTime))

                when(response.status) {
                    200 -> {
                        val replyMutableList : ArrayList<GetReplyResult.Result.Other> = _replyList.value?.toMutableList()
                                as ArrayList<GetReplyResult.Result.Other>

                        if(response.data?.reply != null){
                            val replyInfo: GetReplyResult.Result.Other = getPostReplyInfo(response.data.reply)
                            replyMutableList.add(replyInfo)
                            _replyList.value = replyMutableList
                            _isPostReply.value = Event(true)
                        }
                    }
                    400 -> {
                        _isPostReply.value = Event(false)
                    }
                }

               Timber.d("SUCEESS : $response")
            } catch (e: Throwable) {
                Timber.d("FAILED : $e")
            }
        }
    }

    fun deleteReply() {
        viewModelScope.launch {
            try {
                val response = repository.deleteReply(DeleteReplyRequestData(_replyId))

                when(response.status) {
                    200 -> {
                        val selectedIndex = getSelectedReplyIndex(_replyId)
                        val replyMutableList : ArrayList<GetReplyResult.Result.Other> = _replyList.value?.toMutableList()
                                as ArrayList<GetReplyResult.Result.Other>
                        replyMutableList.removeAt(selectedIndex)
                        _replyList.value = replyMutableList
                        _isDeleteReply.value = Event(true)
                    }
                    400 -> {
                        _isDeleteReply.value = Event(false)
                    }
                }

                Timber.d("SUCCESS : $response")
            } catch (e: Throwable) {
                Timber.d("FAILED : $e")
            }
        }
    }

    private fun getPostReplyInfo(reply: PostReplyResult.Result.Reply) : GetReplyResult.Result.Other {
        return GetReplyResult.Result.Other(
            replyId = reply.replyId,
            replyContent = reply.replyContent,
            replyDate = reply.replyDate,
            userId = reply.userId,
            userNickname = reply.userNickname,
            userImage = reply.userImage
        )
    }

    private fun getSelectedReplyIndex(replyId: Long) : Int {
        if(_replyList.value != null){
            for (i in 0 until _replyList.value!!.size) {
                if(_replyList.value!![i].replyId == replyId){
                    return i
                }
            }
            return -1
        }
        return -1
    }

    fun onClickReplyOption(replyId: Long) {
        _replyId = replyId
        this.replyId = _replyId
        _isClickOption.value = Event(true)
    }
}