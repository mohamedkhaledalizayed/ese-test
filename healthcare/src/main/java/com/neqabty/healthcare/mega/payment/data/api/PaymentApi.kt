package com.neqabty.healthcare.mega.payment.data.api


import com.neqabty.healthcare.mega.payment.data.model.branches.BranchesListModel
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.data.model.payment.PaymentModel
import com.neqabty.healthcare.mega.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.healthcare.mega.payment.data.model.services.ServicesListModel
import com.neqabty.healthcare.mega.payment.data.model.servicesaction.ServiceActionsModel
import retrofit2.Response
import retrofit2.http.*

interface PaymentApi {

    @GET("services/")
    suspend fun getServices(@Query("filter{entity.code}") entity: String, @Query("filter{service_category.name}") category: String = "Subscriptions"): ServicesListModel

    @GET("service_actions/")
    suspend fun getServiceActions(@Query("filter{service.code}") service: String,
                                  @Header("Authorization") token: String): ServiceActionsModel

    @GET("payment/v2/inquiry/{id}/{code}/{number}")
    suspend fun getPaymentDetails(@Path("id") id: String,
                                  @Header("Authorization") token: String,
                                  @Path("code") code: String,
                                  @Path("number") number: String): Response<ReceiptResponse>

    @POST("payment/paymentV2/")
    suspend fun payment(@Body paymentBody: Any,
                        @Header("Authorization") token: String): PaymentModel

    @GET("payment/payment_invoice")
    suspend fun getPaymentStatus(@Header("Authorization") token: String, @Query("transactionId") transaction_id: String): PaymentStatusModel

    @GET("entity_branches")
    suspend fun getBranches(@Query("special-format") platform: String = "android",
                            @Query("filter{entity.code}") code: String): BranchesListModel

}