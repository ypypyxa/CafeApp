package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.event.CoffeeMapEvent
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.viewmodel.CoffeeMapViewModel
import com.pivnoydevelopment.cafeapp.features.locations.util.MapCameraUtils.calculateCameraPosition
import com.pivnoydevelopment.cafeapp.navigation.Menu
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun CoffeeMapScreen(
    navController: NavController = rememberNavController(),
    userLocation: Pair<Double, Double>? = null,
    locations: List<Location> = emptyList(),
    viewModel: CoffeeMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val mapObjects = remember { mutableStateListOf<PlacemarkMapObject>() }

    val locationPermissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val mapView = remember(locationPermissionGranted.value) {
        MapView(context).apply {
            MapKitFactory.initialize(context)
            if (locationPermissionGranted.value) {
                MapKitFactory.getInstance().createUserLocationLayer(mapWindow).apply {
                    isVisible = true
                }
            }
        }
    }

    DisposableEffect(Unit) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()

        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(locations) {
        viewModel.onEvent(CoffeeMapEvent.AddMarkers(locations))
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Карта",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(White)
        ) {
            DoubleLines()

            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize(),
                update = { mapView ->
                    val map = mapView.mapWindow.map

                    map.mapObjects.clear()

                    val allPoints = buildList {
                        userLocation?.let { add(Point(it.first, it.second)) }
                        state.markers.forEach { add(Point(it.point.latitude, it.point.longitude)) }
                    }
                    val cameraPosition = calculateCameraPosition(allPoints)

                    map.move(
                        cameraPosition,
                        Animation(Animation.Type.SMOOTH, 0.5f),
                        null
                    )

                    state.markers.forEach { location ->
                        val placemark = map.mapObjects.addPlacemark().apply {
                            geometry = Point(location.point.latitude, location.point.longitude)
                            setIcon(ImageProvider.fromResource(context, R.drawable.ic_marker))
                            setText(location.name)
                            setTextStyle(
                                TextStyle().apply {
                                    size = 14f
                                    color = ToffieShade.toArgb()
                                    placement = TextStyle.Placement.BOTTOM
                                }
                            )
                            addTapListener { _, _ ->
                                navController.navigate(Menu(location.id, location.name))
                                true
                            }
                            userData = location.id
                        }
                        mapObjects.add(placemark)
                    }
                }
            )
        }
    }
}