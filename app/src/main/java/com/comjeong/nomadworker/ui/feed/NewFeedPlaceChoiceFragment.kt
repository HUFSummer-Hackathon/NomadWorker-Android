package com.comjeong.nomadworker.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentNewFeedPlaceChoiceBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setNewFeedCloseDialog
import com.comjeong.nomadworker.ui.common.util.DialogUtil.setNewFeedSuccessDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NewFeedPlaceChoiceFragment : BaseFragment<FragmentNewFeedPlaceChoiceBinding>(R.layout.fragment_new_feed_place_choice){

    private val viewModel : FeedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindSearchBar()
        bindCancelFeedPosting()

        observeSelectingPlace()
        observePostNewFeed()
    }

    private fun bindViews(){
        binding.tbNewFeedTopBanner.setNavigationOnClickListener {
            navigateUp()
        }

        binding.btnDoneToChoicePlace.setOnClickListener {
            viewModel.postNewFeed()
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
                if(newText.isNullOrEmpty()){
                    handleNextButton(false)
                }
                if(viewModel.isSelectPlace.value == true && newText != viewModel.placeName){
                    handleNextButton(false)
                }
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
            Timber.d("onClickPlace -> ${NewFeedInfo.placeName}")
            binding.svSearchBar.setQuery(NewFeedInfo.placeName, true)
            handleNextButton(true)
        }
    }

    private fun observePostNewFeed() {
        viewModel.isSuccessPost.observe(viewLifecycleOwner) { isSuccess ->
            if(isSuccess){
                setNewFeedSuccessDialog(requireContext())
            }
            else{
                Toast.makeText(requireActivity(), "다시 시도해주세요.", Toast.LENGTH_SHORT)
            }
        }
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