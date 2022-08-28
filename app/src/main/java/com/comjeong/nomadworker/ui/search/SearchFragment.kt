package com.comjeong.nomadworker.ui.search

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSearchBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel : SearchViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindSearchBar()
    }

    private fun bindViews() {
        binding.clSearchOption.setOnClickListener {
            navigate(R.id.action_navigation_search_to_navigation_search_option)
        }
    }

    private fun bindSearchBar() {
        binding.svSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.placeName = query.toString()
                viewModel.getPlaceSearchResult()
                setPlacesSearchResultAdapter()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setPlacesSearchResultAdapter() {
        binding.rvSearchResult.adapter = SearchAdapter(viewModel).apply {
            viewModel.placeList.observe(viewLifecycleOwner) { placeList ->
                submitList(placeList)
            }
        }
    }

}