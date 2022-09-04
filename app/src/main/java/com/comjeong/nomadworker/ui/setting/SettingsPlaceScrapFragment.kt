package com.comjeong.nomadworker.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.PLACE_ID_KEY
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.common.UiState
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.FragmentSettingsPlaceScrapBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateWithBundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsPlaceScrapFragment :
    BaseFragment<FragmentSettingsPlaceScrapBinding>(R.layout.fragment_settings_place_scrap) {

    private val viewModel: SettingsViewModel by sharedViewModel()

    private lateinit var scrapAdapter: PlaceScrapAdapter

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
        observeOpenPlaceDetailEvent()
    }

    private fun observePlaceScrapList() {
        viewModel.uiState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
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

    private fun observeOpenPlaceDetailEvent() {
        viewModel.openPlaceDetailEvent.observe(viewLifecycleOwner, EventObserver<Long> { placeId ->
            openPlaceDetail(placeId)
        })
    }

    private fun openPlaceDetail(placeId: Long) {
        navigateWithBundle(
            R.id.action_scrap_to_place_detail, bundleOf(
                PLACE_ID_KEY to placeId
            )
        )
    }

    private fun initRecyclerView() {
        scrapAdapter = PlaceScrapAdapter(viewModel)
        binding.rvPlaceScrap.adapter = scrapAdapter
    }

    private fun bindViews() {
        binding.tbPlaceScrap.setNavigationOnClickListener {
            navigateUp()
        }
    }
}