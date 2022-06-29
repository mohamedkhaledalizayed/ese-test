package com.neqabty.meganeqabty.payment.data.api

import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.payment.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.meganeqabty.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.meganeqabty.payment.data.model.services.ServicesListModel
import com.neqabty.meganeqabty.payment.data.model.servicesaction.ServiceActionsModel
import retrofit2.http.*

interface PaymentApi {

    @GET("api/services/")
    suspend fun getServices(@Query("code") entity: String): ServicesListModel

    @GET("api/service_actions/")
    suspend fun getServiceActions(@Query("code") service: String): ServiceActionsModel

    @GET("api/payments/v2/inquiry/{id}/{code}/{number}")
    suspend fun getPaymentDetails(@Path("id") id: String,@Path("code") code: String, @Path("number") number: String): ReceiptResponse

    @POST("api/payments")
    suspend fun payment(@Body paymentBody: PaymentBody): PaymentResponse

    @GET("api/payment_methods")
    suspend fun getPaymentMethods(): PaymentMethodsResponse

    @GET("api/transactions/get/{transaction_id}")
    suspend fun getPaymentStatus(@Path("transaction_id") transaction_id: String): PaymentStatusModel

}