package com.comjeong.nomadworker.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentNewFeedPlaceChoiceBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.DialogUtil.setNewFeedCloseDialog
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import timber.log.Timber
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewFeedPlaceChoiceFragment : BaseFragment<FragmentNewFeedPlaceChoiceBinding>(R.layout.fragment_new_feed_place_choice){

    private val viewModel : FeedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindSearchBar()
        bindCancelFeedPosting()

        observeSelectingPlace()
    }

    private fun bindViews(){
        binding.tbNewFeedTopBanner.setNavigationOnClickListener {
            navigateUp()
        }

        binding.btnDoneToChoicePlace.setOnClickListener {
            fillAllInfo()
//            requireActivity().finish()
        }
    }

    private fun bindCancelFeedPosting(){
        binding.tbNewFeedTopBanner.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.close -> {
                    setNewFeedCloseDialog(requireContext())
                    true
                }
                else -> false
            }
        }
    }

    private fun bindSearchBar() {
        binding.svSearchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.placeName = query.toString()
                viewModel.getNewFeedPlaceSearchList()
                setNewFeedPlacesSearchResultAdapter()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d("WRITING_TEXT -> $newText")
                return true
            }
        })
    }

    private fun setNewFeedPlacesSearchResultAdapter() {
        binding.rvNewFeedPlaceSearchResult.adapter = NewFeedAdapter(viewModel).apply {
            viewModel.placeList.observe(viewLifecycleOwner) { placeList ->
                submitList(placeList)
            }
        }
    }

    private fun observeSelectingPlace() {
        viewModel.isSelectPlace.observe(viewLifecycleOwner) { isSelect ->
            Timber.d("onClickPlace -> ${viewModel.placeName}")
            binding.svSearchBar.setQuery(viewModel.placeName, true)
            handleNextButton(true)
        }
    }

    private fun fillAllInfo() {
        val feedContent = RequestBody.create("text/plain".toMediaTypeOrNull(),viewModel.content)
        val placeId = RequestBody.create("text/plain".toMediaTypeOrNull(),viewModel.placeId.toString())
        val feedInfoMap = HashMap<String, RequestBody>()
        feedInfoMap["feed_content"] = feedContent
        feedInfoMap["p_id"] = placeId
        viewModel.map = feedInfoMap
        Timber.d("SUCCESS FILL INFO -> ${viewModel.map.keys}")
    }

    private fun handleNextButton(canEnable: Boolean) {
        if (canEnable) {
            binding.btnDoneToChoicePlace.isEnabled = true
            binding.btnDoneToChoicePlace.setBackgroundResource(R.drawable.bg_blue_radius_10)
        } else {
            binding.btnDoneToChoicePlace.isEnabled = false
            binding.btnDoneToChoicePlace.setBackgroundResource(R.drawable.bg_grey06_radius_10)
        }
    }


}