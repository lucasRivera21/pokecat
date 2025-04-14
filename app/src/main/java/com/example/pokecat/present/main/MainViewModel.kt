package com.example.pokecat.present.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pokecat.api.models.CatResponse
import com.example.pokecat.navigation.DetailsScreen
import com.example.pokecat.present.main.models.Cat
import com.example.pokecat.present.main.models.CatCard
import com.example.pokecat.present.main.models.CatImgResponse
import com.example.pokecat.utils.Credentials.Companion.BG_COLOR_LIST
import com.example.pokecat.utils.Credentials.Companion.MAX_IMG_COMMUNITY
import com.example.pokecat.utils.Credentials.Companion.NAME_CACHE_FILE
import com.example.pokecat.utils.Credentials.Companion.NAME_LIST_IN_MODEL
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
import java.io.FileOutputStream
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

    private val _catList = MutableStateFlow<List<CatCard>>(emptyList())
    val catList = _catList.asStateFlow()

    private val catImgIdList = mutableListOf<String>()
    private var photoUri: Uri? = null

    fun fetchCat() {
        viewModelScope.launch(dispatchers) {
            _isLoading.value = true
            val hasCatInDb = repository.getAllCat().isNotEmpty()

            if (!hasCatInDb) {
                try {
                    val catListResponse = repository.fetchCats()
                    if (catListResponse.isSuccessful) {
                        if (catListResponse.body() != null) {
                            val catListInModel = catListResponse.body()!!
                                .filter { NAME_LIST_IN_MODEL.contains(it.name) }
                            val catList = catListResponseToCatList(catListInModel)
                            insertCat(catList)
                            getAllCatImg()
                        }
                    } else {
                        Log.e(TAG, "server error fetch cat: ${catListResponse.code()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "error fetch cat: ${e.message}")
                }
            }
            getAllCat()
            _isLoading.value = false
        }
    }

    private suspend fun insertCat(catList: List<Cat>) {
        try {
            repository.insertCatList(catList)
        } catch (e: Exception) {
            Log.d(TAG, "error to insert in db")
        }
    }

    private suspend fun getAllCat() {
        try {
            val catFromDb = repository.getAllCat()
            _catList.value = catFromDb.map {
                val catImg = getCatImg(it.imgName)
                CatCard(
                    id = it.id,
                    name = it.name,
                    color = it.color,
                    imgBitmap = catImg,
                    isFounded = it.isFounded > 0
                )
            }
        } catch (e: Exception) {
            Log.d(TAG, "error to get in db: $e")
        }
    }

    private fun getCatImg(imgName: String?): Bitmap? {
        if (imgName == null) {
            return null
        }

        val file = File(context.filesDir, imgName)
        if (!file.exists()) {
            return null
        }

        return BitmapFactory.decodeFile(file.absolutePath)
    }

    private fun catListResponseToCatList(catListResponse: List<CatResponse>): List<Cat> {
        catImgIdList.clear()
        return catListResponse.mapIndexed { index, catResponse ->
            if (catResponse.referenceImageId != null) {
                catImgIdList.add(catResponse.referenceImageId)
            }
            Cat(
                weight = catResponse.weight.metric,
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
                                    val inputStream = response.body()!!.byteStream()
                                    imgBitmap = BitmapFactory.decodeStream(inputStream)
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

    private suspend fun saveImgList(catImgList: List<CatImgResponse>) {
        catImgList.forEach { catImgResponse ->
            val fileName = "cat_img_${catImgResponse.id}.jpeg"

            if (catImgResponse.img != null) {
                try {
                    val file = File(context.filesDir, fileName)

                    if (!file.exists()) {
                        val outputStream = FileOutputStream(file)
                        catImgResponse.img.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream.flush()
                        outputStream.close()

                        repository.updateCatImg(catImgResponse.id, fileName)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "error save img list: ${e.message}")
                }
            }
        }
    }

    fun checkPermission(
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
    ) {
        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            goToCamera(cameraLauncher)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun goToCamera(cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>) {
        val cacheFile = File(context.cacheDir, NAME_CACHE_FILE)
        photoUri =
            FileProvider.getUriForFile(context, "${context.packageName}.provider", cacheFile)
        cameraLauncher.launch(photoUri!!)
    }

    fun navigateScreen(navController: NavController) {
        navController.navigate(DetailsScreen.route + "/-1/$NAME_CACHE_FILE")
    }

    fun onClickCard(navController: NavController, cardId: Int, founded: Boolean) {
        if (!founded) Toast.makeText(context, "This cat is not founded", Toast.LENGTH_SHORT)
            .show() else navController.navigate("${DetailsScreen.route}/$cardId/")
    }
}