package com.codetest.feature.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetest.R
import com.codetest.common.Status
import com.codetest.feature.adapter.LocationListAdapter
import com.codetest.feature.base.BaseActivity
import com.codetest.feature.model.Location
import com.codetest.feature.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class WeatherForecastActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: LocationListAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        setLocationList()
        setupObservers()
        setAddLocationButton()
        getLocationsList()
    }

    private fun getLocationsList() {
        enableListView(false)
        viewModel.getLocations()
    }

    private fun setAddLocationButton() {
        add_location_button.setOnClickListener {
            showAddLocationDialogFragment()
        }
    }

    private fun showAddLocationDialogFragment() {
        val fragment = AddLocationDialogFragment()
        fragment.show(supportFragmentManager, AddLocationDialogFragment.TAG)
    }

    private fun setLocationList() {
        adapter = LocationListAdapter(arrayListOf(), object : LocationListAdapter.LocationListListener {
            override fun onItemDeleteClicked(location: Location) {
                deleteLocation(location)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun deleteLocation(location: Location) {
        viewModel.deleteLocation(location).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        adapter.removeItem(location)
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        showError(it.message)
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.locationResponse.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        enableListView(true)
                        resource.data?.let { data -> retrieveList(data.locations) }
                    }
                    Status.ERROR -> {
                        enableListView(true)
                        showError(it.message)
                    }
                    Status.LOADING -> {
                        enableListView(false)
                    }
                }
            }
        })
    }

    private fun enableListView(enable: Boolean) {
        if (enable) {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun retrieveList(data: List<Location>) {
        adapter.apply {
            addItems(data)
        }
    }

    private fun showError(message: String?) {
        AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.error_title))
                .setMessage(message ?: getString(R.string.message_something_went_wrong))
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
                .create()
                .show()
    }
}