package com.example.pokecat.present.details

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokecat.machineLearning.Classifier
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
    private val classifier: Classifier,
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
                } catch (e: Exception) {
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

    fun identifyCat(photoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            var bitmap: Bitmap? = null

            try {
                bitmap = getImgFromUri(photoName)
            } catch (e: Exception) {
                Log.d(TAG, "error to get img: $e")
            }

            if (bitmap != null) {
                val results = classifier.recognizeImage(bitmap)
                Log.d(TAG, "results: $results")

                if (results.isNotEmpty()) {
                    val catIdentifier = results.first().id.split("_")
                        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

                    val catCardEntity = repository.getCardByName(catIdentifier)

                    var imgBitmap: Bitmap? = null
                    try {
                        imgBitmap = getImg(catCardEntity.imgName!!)
                    } catch (e: Exception) {
                        Log.d(TAG, "error to get img: $e")
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
                }
            }

            _isLoading.value = false
        }
    }

    private fun getImgFromUri(photoName: String): Bitmap {
        val cacheFile = File(context.cacheDir, photoName)
        return android.graphics.BitmapFactory.decodeFile(cacheFile.absolutePath)
    }

    private fun getImg(imgName: String): Bitmap {
        val cacheFile = File(context.filesDir, imgName)
        return android.graphics.BitmapFactory.decodeFile(cacheFile.absolutePath)
    }
}