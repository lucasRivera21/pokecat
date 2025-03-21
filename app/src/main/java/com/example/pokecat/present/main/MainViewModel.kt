package com.example.pokecat.present.main

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.present.main.models.Cat
import com.example.pokecat.present.main.models.CatImgResponse
import com.example.pokecat.present.main.models.Weight
import com.example.pokecat.utils.Credentials.Companion.BG_COLOR_LIST
import com.example.pokecat.utils.Credentials.Companion.MAX_IMG_COMMUNITY
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainTask,
    private val dispatchers: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _catList = MutableStateFlow<List<Cat>>(emptyList())
    val catList = _catList.asStateFlow()

    private val catImgIdList = mutableListOf<String>()

    init {
        fetchCat()
    }

    private fun fetchCat() {
        viewModelScope.launch(dispatchers) {
            _isLoading.value = true
            try {
                val catListResponse = repository.fetchCats()
                if (catListResponse.isSuccessful) {
                    if (catListResponse.body() != null) {
                        Log.d(TAG, "fetch cat: ${catListResponse.body()!!}")
                        _catList.value = catListResponseToCatList(catListResponse.body()!!)
                        getAllCatImg()
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
        catImgIdList.clear()
        return catListResponse.mapIndexed { index, catResponse ->
            if (catResponse.referenceImageId != null) {
                catImgIdList.add(catResponse.referenceImageId)
            }
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

    private suspend fun getAllCatImg() {
        Log.d(TAG, "cat img id list: $catImgIdList")
        coroutineScope {
            while (catImgIdList.isNotEmpty()) {
                val firstsCatImgId = catImgIdList.take(MAX_IMG_COMMUNITY)
                val request = firstsCatImgId.map {
                    async {
                        var imgBitmap: Bitmap? = null
                        try {
                            val response = repository.fetchCatImg(it)
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    imgBitmap = response.body()!!
                                }
                            } else {
                                Log.e(TAG, "server error fetch cat img: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "error fetch cat img: ${e.message}")
                        }

                        CatImgResponse(id = it, img = imgBitmap)
                    }
                }

                val catImgResponseList = request.awaitAll()

                saveImgList(catImgResponseList)

                catImgIdList.removeAll(firstsCatImgId)
            }
        }
    }

    private fun saveImgList(catImgList: List<CatImgResponse>) {
        Log.d(TAG, "save img list: $catImgList")

        val directory = File(context.filesDir, "cat_img")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        catImgList.forEach { catImgResponse ->
            val fileName = "cat_img_${catImgResponse.id}.jpg"
            val file = File(directory, fileName)

            if(catImgResponse.img != null){
                try {
                    file.outputStream().use { outputStream ->
                        catImgResponse.img.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "error save img list: ${e.message}")
                }
            }
        }
    }
}