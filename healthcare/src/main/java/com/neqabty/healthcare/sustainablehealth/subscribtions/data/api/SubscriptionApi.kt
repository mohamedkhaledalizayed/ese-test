package com.neqabty.healthcare.sustainablehealth.subscribtions.data.api

import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.TermsBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.UpdatePackageBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.relationstypes.RelationsTypesModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.subscription.SubscriptionModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms.TermsModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.updatepackage.UpdatePackageModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SubscriptionApi {
    @POST("vendor/subscribtions/request")
    suspend fun addSubscription(
        @Header("Authorization") token: String,
        @Body subscribePostBodyRequest: SubscribePostBodyRequest): SubscriptionModel

    @POST("vendor/subscribtions/request-update")
    suspend fun updatePackage(@Header("Authorization") token: String, @Body updatePackageBody: UpdatePackageBody): UpdatePackageModel

    @GET("general-Lockups")
    suspend fun getRelations(): RelationsTypesModel

    @POST("insurance-policy")
    suspend fun getTermsAndConditions(@Body termsBody: TermsBody): TermsModel

}
