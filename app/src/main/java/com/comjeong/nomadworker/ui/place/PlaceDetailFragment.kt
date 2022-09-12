package com.comjeong.nomadworker.ui.place

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_HOVER_MOVE
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.CAMERA_ZOOM
import com.comjeong.nomadworker.common.Constants.PLACE_ID_KEY
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.databinding.FragmentPlaceDetailBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PlaceDetailFragment :
    BaseFragment<FragmentPlaceDetailBinding>(R.layout.fragment_place_detail),
    OnMapReadyCallback {

    private val viewModel: PlaceDetailViewModel by viewModel()
    private var placeLatitude: Double = 0.0
    private var placeLongitude: Double = 0.0

    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val placeId = requireArguments().getLong(PLACE_ID_KEY)
        Timber.d("$placeId")
        viewModel.placeId = placeId

        viewModel.getPlaceDetailByPlaceId()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        observePlaceDetailInfo()
        observeMessage()
        bindEvaluationClick()
        bindGoogleMapScroll()

        mMapView = binding.mvPlaceMap
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

    }

    private fun observeMessage() {
        viewModel.message.observe(viewLifecycleOwner, EventObserver { message ->
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    private fun observePlaceDetailInfo() {
        viewModel.placeDetailInfo.observe(viewLifecycleOwner) { detailInfo ->
            binding.placeInfo = detailInfo

            placeLatitude = detailInfo.placeLatitude!!
            placeLongitude = detailInfo.placeLongitude!!

            Timber.d("$placeLatitude")
            Timber.d("$placeLongitude")

        }

        viewModel.isScraped.observe(viewLifecycleOwner) { isScraped ->
            binding.isScraped = isScraped
        }
    }

    private fun bindViews() {
        binding.tbPlaceDetail.setNavigationOnClickListener {
            navigateUp()
        }

        binding.ivScrap.setOnClickListener {
            viewModel.postPlaceScrap()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val placeLocation = getPlaceLocation()

        mGoogleMap = googleMap
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL


        mGoogleMap.apply {
            moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation, CAMERA_ZOOM))
            val options = MarkerOptions()
            options.position(placeLocation)
            this.addMarker(options)
        }
    }

    private fun getPlaceLocation(): LatLng {
        return LatLng(placeLatitude, placeLongitude)
    }

    private fun bindEvaluationClick() {
        binding.btnEvaluation.setOnClickListener {
            setEvaluationDialog()
        }
    }

    private fun bindGoogleMapScroll() {
        binding.ivMapContainer.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action) {
                    MotionEvent.ACTION_UP -> {
                        binding.svPlaceDetail.requestDisallowInterceptTouchEvent(false)
                        return false
                    }
                    MotionEvent.ACTION_DOWN -> {
                        Timber.d("Down")
                        binding.svPlaceDetail.requestDisallowInterceptTouchEvent(true)
                        return false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        binding.svPlaceDetail.requestDisallowInterceptTouchEvent(true)
                        return false
                    }
                }
                return true
            }
        })
    }

    private fun setEvaluationDialog() {
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme)
        val view = LayoutInflater.from(requireActivity()).inflate(
            R.layout.dialog_evaluation,
            requireActivity().findViewById<ConstraintLayout>(R.id.dialog_layout)
        )

        builder.setView(view)

        val placeName = view.findViewById<TextView>(R.id.tv_place_name)
        val ratingNumber = view.findViewById<TextView>(R.id.tv_evaluation_number)
        val alertMessage = view.findViewById<TextView>(R.id.tv_alert)
        val evaluationButton = view.findViewById<Button>(R.id.btn_add_evaluation)
        val closeButton = view.findViewById<Button>(R.id.btn_close)
        val ratingBar = view.findViewById<RatingBar>(R.id.rb_rating)

        placeName.text = binding.tvPlaceName.text
        ratingNumber.text = ratingBar.rating.toString()

        val alertDialog = builder.create()

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingNumber.text = rating.toString()
        }

        evaluationButton.setOnClickListener {
            if (ratingBar.rating == 0f) {
                alertMessage.visibility = View.VISIBLE
            } else {
                viewModel.placeRate = ratingBar.rating
                viewModel.updatePlaceRate()
                alertDialog.dismiss()
            }
        }

        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        alertDialog.show()
    }

}