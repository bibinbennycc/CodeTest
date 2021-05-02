package com.codetest.feature.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.codetest.R
import com.codetest.common.Status.*
import com.codetest.feature.base.BaseDialogFragment
import com.codetest.feature.model.LocationRequest
import com.codetest.feature.model.Status
import com.codetest.feature.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_add_location.*
import javax.inject.Inject

class AddLocationDialogFragment : BaseDialogFragment() {

    companion object{
        const val TAG = "AddLocationDialogFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private var selectedStatus: Status? = null

    override fun getLayoutId(): Int = R.layout.dialog_add_location

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        setupToolbarBackButton(add_location_toolbar)
        setupWeatherSpinner()
        setupAddLocationButton()
    }

    private fun setupAddLocationButton() {
        add_location_button.setOnClickListener {
            if(isFormValid()){
                addLocation()
            }
        }
    }

    private fun addLocation() {
        selectedStatus?.let { status ->
            val name = add_location_name_field.text.toString().trim()
            val temperature = add_location_temperature_field.text.toString().trim()
            val location = LocationRequest(name,temperature,status)

            viewModel.addLocation(location).observe(this, Observer { resource ->
                resource?.let { result ->
                    when (result.status) {
                        SUCCESS -> {
                            progressBar.visibility = View.GONE
                            result.data?.let {
                                viewModel.updateLocation(it)
                            }
                            dismiss()
                        }
                        ERROR -> {
                            progressBar.visibility = View.GONE
                            showError(result.message)
                        }
                        LOADING -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    private fun isFormValid(): Boolean {
        var isValid = true
        if(add_location_name_field.text.toString().trim().isEmpty()){
            add_location_name_layout.error = getString(R.string.required_field)
            isValid = false
        }else{
            add_location_name_layout.error = null
        }

        if(selectedStatus == null){
            add_location_status_spinner_error_view.visibility = View.VISIBLE
            isValid = false
        }else{
            add_location_status_spinner_error_view.visibility = View.GONE
        }

        if(add_location_temperature_field.text.toString().trim().isEmpty()){
            add_location_temp_layout.error = getString(R.string.required_field)
            isValid = false
        }else{
            add_location_temp_layout.error = null
        }
        return isValid
    }

    private fun setupWeatherSpinner() {
        val adapter = ArrayAdapter<Status>(requireContext(),android.R.layout.simple_spinner_dropdown_item, Status.values())
        add_location_status_spinner.adapter = adapter
        add_location_status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStatus = Status.values()[position]
            }
        }
    }
}