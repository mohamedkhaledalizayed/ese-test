package com.neqabty.healthcare.modules.subscribtions.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.neqabty.healthcare.modules.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.modules.subscribtions.data.source.SubscriptionSource
import com.neqabty.healthcare.modules.subscribtions.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(private val subscriptionSource: SubscriptionSource) :
    SubscriptionRepository {
    override fun addSubscription(
        name: String,
        birthDate: String,
        email: String,
        address: String,
        job: String,
        mobile: String,
        nationalId: String,
        syndicateId: Int,
        packageId: String,
        referralNumber: String,
        personalImage: String,
        fronIdImage: String,
        backIdImage: String
    ): Flow<Boolean> {
//        val p = personalImage.toBase64()
        val sub = SubscribePostBodyRequest(
            address = address,
            birthDate = birthDate,
            email = email,
            fullName = name,
            job = job,
            mobile = mobile,
            nationalId = nationalId,
            packageId = packageId,
            referralNumber = referralNumber,
            syndicateId = syndicateId,
            personalImage = personalImage,
            frontIdImage = fronIdImage,
            backIdImage = backIdImage
        )
        return flow {
            emit(
                subscriptionSource.addSubscription(
                    sub
                )
            )
        }
    }
}

private fun String.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile(this)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
    val imageBytes = byteArrayOutputStream.toByteArray()
    val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
    return imageString
}
