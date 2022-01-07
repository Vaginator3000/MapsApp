package com.template.mapsapp.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapsapp.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import android.graphics.Color
import com.yandex.mapkit.map.RotationType
import android.graphics.PointF
import android.util.Log
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.coroutine.TedPermission
import com.template.mapsapp.R
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import com.yandex.mapkit.map.CompositeIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Сюда пока сильно не смотрите, просто разбираюсь с картами. Потом будет рефактор

class MapFragment : Fragment(), UserLocationObjectListener {
    private val binding: FragmentMapBinding by viewBinding(CreateMethod.INFLATE)

    private val mapKitFactory by lazy { MapKitFactory.getInstance() }
    private val userLocationLayer by lazy { mapKitFactory.createUserLocationLayer(binding.mapview.mapWindow) }

    private val mapViewModel by lazy { ViewModelProvider(this).get(MapViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initMap()
        CoroutineScope(Dispatchers.Default).launch {
            if (requireLocationPermission())
                this.launch(Dispatchers.Main) {
                    setMapOnCurrentLocation()
                }
        }
        return binding.root
    }

    private suspend fun requireLocationPermission(): Boolean {

       /* val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                result = true
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(
                    requireContext(),
                    "Доступ к местоположению запрещен!",
                    Toast.LENGTH_SHORT
                ).show();
                result = false
            }
        }*/

        TedPermission.create()
        //    .setPermissionListener(permissionListener)
         //   .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()

        val result =
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        return result
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapKitFactory.onStart()
        binding.mapview.onStart()
    }

    private fun initMap() {
      //  MapKitFactory.setApiKey(getString(R.string.mapkit_api_key))
        MapKitFactory.initialize(this.requireContext())

        binding.mapview.map.isRotateGesturesEnabled = false

        binding.mapview.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    private suspend fun setMapOnCurrentLocation() {

        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true

        userLocationLayer.setObjectListener(this)

    }

    override fun onStop() {
        super.onStop()
      //  MapKitFactory.getInstance().onStop()
        mapKitFactory.onStop()
        binding.mapview.onStop()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        Log.e("AA", "2")
        with(binding) {
            userLocationLayer.setAnchor(
                PointF((mapview.width * 0.5).toFloat(), (mapview.height * 0.5).toFloat()),
                PointF((mapview.width * 0.5).toFloat(), (mapview.height * 0.83).toFloat() )
            )
        }

        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.user_arrow
            )
        )

        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()

       /* pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(requireContext(), R.drawable.icon),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(1f)
        )*/

        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(requireContext(), R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )

        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }
}