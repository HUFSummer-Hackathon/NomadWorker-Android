package com.comjeong.nomadworker.ui.search

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentSearchOptionBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.popBackStack
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchOptionFragment : BaseFragment<FragmentSearchOptionBinding>(R.layout.fragment_search_option){

    val viewModel : SearchViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.tbSearchOption.setNavigationOnClickListener {
            navigateUp()
        }

        binding.btnComplete.setOnClickListener {
            viewModel.location = getSelectedLocation()
            viewModel.storetype = getSelectedStoretype()
            popBackStack()
        }
    }

    private fun getSelectedLocation(): String {
        return when (binding.cgOptionLocation.checkedChipId) {

            binding.chipSeoul.id -> binding.chipSeoul.text.toString()

            binding.chipBusan.id -> binding.chipBusan.text.toString()

            binding.chipJeju.id -> binding.chipJeju.text.toString()

            binding.chipGangneung.id -> binding.chipGangneung.text.toString()

            else -> ""
        }
    }

    private fun getSelectedStoretype(): String {
        return when (binding.cgOptionPlace.checkedChipId) {

            binding.chipCafe.id -> binding.chipCafe.text.toString()

            binding.chipOffice.id -> binding.chipOffice.text.toString()

            else -> ""
        }
    }
}