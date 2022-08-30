package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.UiState
import com.comjeong.nomadworker.databinding.FragmentSettingsPlaceScrapBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsPlaceScrapFragment :
    BaseFragment<FragmentSettingsPlaceScrapBinding>(R.layout.fragment_settings_place_scrap) {

    private val viewModel: SettingsViewModel by sharedViewModel()
    private val scrapAdapter by lazy { PlaceScrapAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getPlaceScrapListByUserId()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        observePlaceScrapList()
    }

    private fun observePlaceScrapList() {
        viewModel.uiState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    is UiState.Success -> {
//                        dismissLoadingSpinner()
                        initRecyclerView()
                        scrapAdapter.submitList(it.data)
                        binding.isEmpty = false
                    }
                    is UiState.Empty -> {
//                        dismissLoadingSpinner()
                        binding.isEmpty = true
                    }
                    is UiState.Error -> {
//                        dismissLoadingSpinner()
                    }
                    is UiState.Loading -> {
//                        showLoadingSpinner(requireContext())
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun initRecyclerView() {
        binding.rvPlaceScrap.adapter = scrapAdapter
    }

    private fun bindViews() {
        binding.tbPlaceScrap.setNavigationOnClickListener {
            navigateUp()
        }
    }
}