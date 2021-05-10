package com.codetest.feature.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.codetest.common.Resource
import com.codetest.feature.model.Location
import com.codetest.feature.model.LocationModel
import com.codetest.feature.model.LocationRequest
import com.codetest.feature.repository.AppRepository
import com.codetest.util.KeyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: AppRepository, context: Context) : ViewModel() {

    private var _locationResponse: MutableLiveData<Resource<LocationModel>> = MutableLiveData()
    private val apiKey = KeyUtil.getKey(context)

    val locationResponse: LiveData<Resource<LocationModel>>
        get() = _locationResponse

    private fun setLocations(locations: Resource<LocationModel>) {
        _locationResponse.value = locations
    }

    fun updateLocation(location: Location) {
        val locations: ArrayList<Location> = _locationResponse.value?.data?.locations
                ?: arrayListOf()
        locations.add(location)
        _locationResponse.value = Resource.success(LocationModel(locations))
    }

    fun getLocations() {
        viewModelScope.launch {
            setLocations(mainRepository.getLocations(apiKey))
        }
    }

    fun addLocation(location: LocationRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        val result = mainRepository.addLocation(apiKey, location)
        emit(result)
    }

    fun deleteLocation(location: Location) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        val result = mainRepository.deleteLocation(apiKey, location)
        emit(result)
    }
}