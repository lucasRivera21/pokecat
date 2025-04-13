package com.example.pokecat.present.details

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokecat.present.details.models.DetailsToShow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

const val TAG = "DetailsViewModel"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _catDetail = MutableStateFlow<DetailsToShow?>(null)
    val catDetail = _catDetail.asStateFlow()

    fun getCard(catId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val catCardEntity = repository.getCard(catId)

            var imgBitmap: Bitmap? = null

            if (catCardEntity.imgName != null) {
                try {
                    imgBitmap = getImg(catCardEntity.imgName)
                }catch (e: Exception){
                    Log.d(TAG, "error to get img: $e")
                }

            }

            _catDetail.value = DetailsToShow(
                weight = catCardEntity.weight,
                name = catCardEntity.name,
                temperament = catCardEntity.temperament,
                origin = catCardEntity.origin,
                description = catCardEntity.description,
                img = imgBitmap,
                color = catCardEntity.color
            )

            _isLoading.value = false
        }
    }

    private fun getImg(imgName: String): Bitmap {
        val cacheFile = File(context.filesDir, imgName)
        return android.graphics.BitmapFactory.decodeFile(cacheFile.absolutePath)
    }
}