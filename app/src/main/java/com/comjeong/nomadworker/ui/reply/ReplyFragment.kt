package com.comjeong.nomadworker.ui.reply

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ConcatAdapter
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.databinding.FragmentFeedReplyBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.customview.CustomDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReplyFragment : BaseFragment<FragmentFeedReplyBinding>(R.layout.fragment_feed_reply) {

    private val viewModel: ReplyViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        viewModel.authorList.observe(viewLifecycleOwner) { authorList ->
            authorAdapter.submitList(authorList)
        }
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