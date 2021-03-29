package com.example.ubereatsoffline

import android.content.Intent
import android.location.Address
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ubereatsoffline.adapters.RestaurantAdapter
import com.example.ubereatsoffline.viewmodels.RestaurantViewModel
import com.example.ubereatsoffline.databinding.ActivityMainBinding
import com.example.ubereatsoffline.listeners.RestaurantActionListener
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

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
        fusedLocationClient = FusedLocationProviderClient(this)
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            .create(RestaurantViewModel::class.java)

        mViewModel.getRestaurants().observe(this, Observer {
            it?.let {
                restaurantList.addAll(it)
                setupRestaurantRv()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if(Utils.isRefreshRequired) {
            mViewModel.getRestaurants().observe(this, Observer {
                it?.let {
                    restaurantList.clear()
                    restaurantList.addAll(it)
                    mAdapter.updateList(restaurantList)
                    mAdapter.notifyDataSetChanged()
                }
            })
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

    override fun onRestaurantClicked(restaurant: Restaurant) {
        startActivity(Intent(this, RestaurantDetailsActivity::class.java).apply {
            putExtra("restaurant", Gson().toJson(restaurant).toString())
        })
    }

}