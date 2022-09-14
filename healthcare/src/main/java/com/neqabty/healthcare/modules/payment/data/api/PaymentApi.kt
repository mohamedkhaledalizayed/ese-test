package com.neqabty.healthcare.modules.payment.data.api

import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.PaymentHomeBody
import com.neqabty.meganeqabty.payment.data.model.branches.BranchesListModel
import com.neqabty.meganeqabty.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.payment.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.meganeqabty.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.meganeqabty.payment.data.model.services.ServicesListModel
import com.neqabty.meganeqabty.payment.data.model.servicesaction.ServiceActionsModel
import retrofit2.Response
import retrofit2.http.*

interface PaymentApi {

    @POST("payments")
    suspend fun payment(@Body paymentBody: SehaPaymentBody,
                        @Header("Authorization") token: String): SehaPaymentResponse

    @GET("payment_methods")
    suspend fun getPaymentMethods(@Header("Authorization") token: String): PaymentMethodsResponse


}