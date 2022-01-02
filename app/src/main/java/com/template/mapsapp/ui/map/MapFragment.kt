package com.template.mapsapp.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapsapp.R
import com.template.mapsapp.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition


class MapFragment : Fragment() {
    private val mapViewModel by lazy { ViewModelProvider(this).get(MapViewModel::class.java) }

    private val binding: FragmentMapBinding by viewBinding(CreateMethod.BIND)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    override fun onResume() {
        super.onResume()
        initMap()
    }

    private fun initMap() {

        MapKitFactory.setApiKey(getString(R.string.mapkit_api_key))
        MapKitFactory.initialize(this.requireContext())

        binding.mapview.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    override fun onStop() {
        super.onStop()
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }
}