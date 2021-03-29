package com.example.ubereatsoffline

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ubereatsoffline.utils.IntentFactory
import com.example.ubereatsoffline.adapters.RestaurantAdapter
import com.example.ubereatsoffline.viewmodels.RestaurantViewModel
import com.example.ubereatsoffline.databinding.ActivityMainBinding
import com.example.ubereatsoffline.listeners.RestaurantActionListener
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.utils.CalendarContentResolver
import com.example.ubereatsoffline.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity(), RestaurantActionListener {

    private lateinit var mBinding: ActivityMainBinding
    private var restaurantList = ArrayList<Restaurant>()
    private lateinit var mAdapter: RestaurantAdapter
    private lateinit var mViewModel: RestaurantViewModel
    private val locations = ArrayList<Address>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var destination: Address
    private lateinit var source: Address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        getPermissions()
        fusedLocationClient = FusedLocationProviderClient(this)
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            .create(RestaurantViewModel::class.java)

        mViewModel.getRestaurants().observe(this, Observer {
            it?.let {
                restaurantList.addAll(it)
                setupRestaurantRv()
            }
        })

        createNotificationChannel()
        createNotification()
    }

    private fun createNotification() {

        val builder = NotificationCompat.Builder(this, "1234")
            .setContentTitle("Hello this is a notification")
            .setSmallIcon(R.drawable.ic_paytm)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("123")
        }
        with(NotificationManagerCompat.from(this)) {
            notify(1234, builder.build())
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ride_notification"
            val descriptionText = "Time to go"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("123", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun setupRestaurantRv() {
        mAdapter = RestaurantAdapter(restaurantList, this, this)
        mBinding.restaurantRv.apply {
            adapter = mAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getPermissions() {
        checkPermissions(
            42,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun checkPermissions(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(
                this,
                p
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(this, permissionsId, callbackId)
    }

    @SuppressLint("MissingPermission")
    private fun setSource() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getPermissions()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it == null) {
                Utils.showLongToast("location not found", this)
            } else {
                val location = Geocoder(this).getFromLocation(it.latitude, it.longitude, 1)
                if (location.size > 0) {
                    source = location[0]
                    if (source.getAddressLine(0)
                            ?.isNotEmpty() == true && destination.getAddressLine(0)
                            ?.isNotEmpty() == true
                    )
                        startActivity(
                            IntentFactory.getOneTapRideIntent(
                                this,
                                source.getAddressLine(0),
                                destination.getAddressLine(0),
                                getPrice()
                            )
                        )
                } else {
                    Utils.showLongToast("no source found", this)
                }
            }
        }
    }

    private fun getPrice() = (Random.nextFloat() * 100 + 100).roundToInt().toFloat()


    private fun setDestination() {
        val resolver = CalendarContentResolver(this)
        resolver.getEvents().forEach {
            val location = Geocoder(this).getFromLocationName(it, 1)
            if (location.size > 0)
                locations.add(location[0])
        }
        if (locations.size > 0) {
            destination = locations[0]
            setSource()
        } else {
            Utils.showLongToast("no destination found", this)
        }
    }

    override fun onRestaurantClicked(restaurant: Restaurant) {
        startActivity(Intent(this, RestaurantDetailsActivity::class.java).apply {
            putExtra("id", restaurant.id)
        })
    }

}