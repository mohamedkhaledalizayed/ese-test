package com.neqabty.chefaa.modules.orders.presentation.view.placeprescriptionscreen

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.neqabty.chefaa.core.utils.AppUtils
//import com.neqabty.chefaa.core.utils.Resource
//import com.neqabty.chefaa.modules.orders.domain.interactors.PlacePrescriptionUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import javax.inject.Inject
//
//@HiltViewModel
//class PlacePrescriptionViewModel @Inject constructor(private val placePrescriptionUseCase: PlacePrescriptionUseCase) :
//    ViewModel() {
//    val placeImagesResult = MutableLiveData<Resource<String>>()
//    fun placePrescriptionImages(
//        order: RequestBody,
//        images: ArrayList<MultipartBody.Part>
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            placeImagesResult.postValue(Resource.loading(data = null))
//            try {
//                placePrescriptionUseCase.build(
//                    order, images
//                ).collect {
//                    placeImagesResult.postValue(Resource.success(data = it))
//                }
//            } catch (exception: Throwable) {
//                placeImagesResult.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
//            }
//        }
//    }
//
//}