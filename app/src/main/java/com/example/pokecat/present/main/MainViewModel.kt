package com.example.pokecat.present.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.present.main.models.Cat
import com.example.pokecat.present.main.models.Weight
import com.example.pokecat.utils.Credentials.Companion.BG_COLOR_LIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _catList = MutableStateFlow<List<Cat>>(emptyList())
    val catList = _catList.asStateFlow()

    init {
        fetchCat()
    }

    private fun fetchCat() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val catListResponse = repository.fetchCats()
                if (catListResponse.isSuccessful) {
                    if (catListResponse.body() != null) {
                        Log.d(TAG, "fetch cat: ${catListResponse.body()!!}")
                        _catList.value = catListResponseToCatList(catListResponse.body()!!)
                    }
                } else {
                    Log.e(TAG, "server error fetch cat: ${catListResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "error fetch cat: ${e.message}")
            }
            _isLoading.value = false
        }
    }

    private fun catListResponseToCatList(catListResponse: List<CatResponse>): List<Cat> {
        return catListResponse.mapIndexed { index, catResponse ->
            Cat(
                weight = Weight(catResponse.weight.imperial, catResponse.weight.metric),
                id = catResponse.id,
                name = catResponse.name,
                temperament = catResponse.temperament,
                origin = catResponse.origin,
                description = catResponse.description,
                referenceImageId = catResponse.referenceImageId,
                color = BG_COLOR_LIST[index % BG_COLOR_LIST.size]
            )
        }
    }
}