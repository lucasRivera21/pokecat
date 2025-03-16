package com.example.pokecat.present.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    init {
        fetchCat()
    }

    private fun fetchCat() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val catListResponse = repository.fetchCats()
                if (catListResponse.isSuccessful) {
                    if(catListResponse.body() != null){
                        Log.d(TAG, "fetch cat: ${catListResponse.body()}")
                    }
                }else{
                    Log.e(TAG, "server error fetch cat: ${catListResponse.code()}")
                }
            }catch (e: Exception){
                Log.e(TAG, "error fetch cat: ${e.message}")
            }

        }
    }
}