package com.neqabty.healthcare.pharmacymart.orders.domain.usecases



import com.neqabty.healthcare.pharmacymart.orders.domain.entity.addorder.AddOrderEntity
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val ordersRepository: PharmacyMartOrdersRepository) {
    fun build(items:List<String>, addressId:Int, deliveryNote:String, deliveryMobile:String,
              orderByText:String, deviceInfo:String, currentLocation:String): Flow<AddOrderEntity> {
        return ordersRepository.placeOrder(
            items = items,
            addressId = addressId,
            deliveryNote = deliveryNote,
            deliveryMobile = deliveryMobile,
            orderByText = orderByText,
            deviceInfo = deviceInfo,
            currentLocation = currentLocation
        )
    }
}