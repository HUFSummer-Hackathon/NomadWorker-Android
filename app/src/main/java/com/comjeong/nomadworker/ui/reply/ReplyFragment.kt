package com.comjeong.nomadworker.ui.reply

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ConcatAdapter
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_CONTENT_KEY
import com.comjeong.nomadworker.common.Constants.PLACE_NAME_KEY
import com.comjeong.nomadworker.common.Constants.USER_IMAGE_KEY
import com.comjeong.nomadworker.common.Constants.USER_NICKNAME_KEY
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.databinding.FragmentFeedReplyBinding
import com.comjeong.nomadworker.domain.model.reply.Author
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.CustomDialog
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.common.RootViewInsetsCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author 이재성
 *
 * Adapter 두개 [ReplyAuthorTopAdapter], [ReplyOthersAdapter]를 ConcatAdapter로 연결해서 만들면 됨
 */

class ReplyFragment : BaseFragment<FragmentFeedReplyBinding>(R.layout.fragment_feed_reply) {

    private val viewModel: ReplyViewModel by sharedViewModel()
    private var userNickname: String = ""
    private var userImage: String = ""
    private var placeName: String = ""
    private var feedContent: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userNickname = requireArguments().getString(USER_NICKNAME_KEY).toString()
        userImage = requireArguments().getString(USER_IMAGE_KEY).toString()
        viewModel.placeName = requireArguments().getString(PLACE_NAME_KEY).toString()
        feedContent = requireArguments().getString(FEED_CONTENT_KEY).toString()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListAdapter()
        setKeyboardScroll()

        bindView()
        bindDoneToWriteComment()

        observeClickReplyOption()
        observeDeleteReply()
        observePostReply()

        viewModel.getReply()
    }

    private fun bindView() {
        binding.tbReply.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun bindDoneToWriteComment() {
        binding.btnDoneToWriteComment.setOnClickListener {
            val inputText = binding.etCommentInput.text.toString()
            if(inputText.isNotEmpty()){
                viewModel.replyContent = inputText
                viewModel.postReply(getTime())
            }
            else{
                Toast.makeText(requireActivity(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observePostReply() {
        viewModel.isPostReply.observe(viewLifecycleOwner, EventObserver<Boolean> {  isSuccess ->
            if(isSuccess){
                removeKeyboardFromDisplay()
                clearCommentInput()
            }
        })
    }

    private fun observeClickReplyOption() {
        viewModel.isClickOption.observe(viewLifecycleOwner, EventObserver<Boolean> {
            setReplyOptionView()
        })
    }

    private fun observeDeleteReply() {
        viewModel.isDeleteReply.observe(viewLifecycleOwner, EventObserver<Boolean> { isSuccess ->
            if(isSuccess){
                Toast.makeText(requireActivity(),"댓글을 삭제했습니다.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireActivity(),"다시 시도해주세요.",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setListAdapter() {
        val authorAdapter = ReplyAuthorTopAdapter(viewModel,viewLifecycleOwner)
        val otherAdapter = ReplyOthersAdapter(viewModel)
        binding.rvFeedReply.adapter = ConcatAdapter(authorAdapter,otherAdapter)

        authorAdapter.submitList(listOf(Author(userNickname,userImage,viewModel.placeName,feedContent)))
        viewModel.replyList.observe(viewLifecycleOwner) { replyList ->
            Timber.d("댓글 리스트 : $replyList")
            otherAdapter.submitList(replyList)
        }
    }

    private fun setDeleteConfirmDialog() {
        val dialog = CustomDialog(requireActivity())
        dialog.showDialog("댓글을 삭제하시겠습니까?", "선택한 댓글이 삭제되므로 신중히 선택해주세요.",true)
        dialog.setDialogButtonClickListener(object :
            CustomDialog.DialogButtonClickListener {
            override fun onClicked(event: CustomDialog.DialogEvent) {
                when(event) {
                    CustomDialog.DialogEvent.POSITIVE -> {
                        // 댓글 삭제를 진행
                        viewModel.deleteReply()
                    }
                    else -> Unit
                }
            }
        })
    }

    private fun setReplyOptionView() {
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_reply_option, null)
        val bottomSheetDialog = BottomSheetDialog(requireActivity())

        bottomSheetView.findViewById<ConstraintLayout>(R.id.cl_delete_container).setOnClickListener {
            setDeleteConfirmDialog()
        }
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun getTime(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val getTime = dateFormat.format(date)
        return getTime
    }

    private fun removeKeyboardFromDisplay() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etCommentInput.windowToken, 0)
    }

    private fun clearCommentInput() {
        binding.etCommentInput.text?.clear()
        binding.etCommentInput.clearFocus()
    }

    private fun setKeyboardScroll() {
        val onLayoutChangeListener =
            View.OnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                if (bottom < oldBottom) {
                    binding.rvFeedReply.scrollBy(0, oldBottom - bottom)
                }
            }

        binding.rvFeedReply.apply {
            addOnLayoutChangeListener(onLayoutChangeListener)
        }
    }

}