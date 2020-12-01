package com.neqabty.presentation.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.efinance.mobilepaymentsdk.PaymentCreationCallback;
import com.efinance.mobilepaymentsdk.PaymentCreationResponse;
import com.efinance.mobilepaymentsdk.PaymentException;
import com.efinance.mobilepaymentsdk.PaymentStatusInquiryCallback;
import com.efinance.mobilepaymentsdk.PaymentStatusInquiryResponse;
import com.neqabty.MainActivity;
import com.neqabty.R;

public class MobilePaymentStatusInquiryCallback implements PaymentStatusInquiryCallback {

    private Context context;

    public MobilePaymentStatusInquiryCallback(Context context){
        this.context = context;
    }

    @Override
    public void onSuccess(PaymentStatusInquiryResponse response) {
//        Log.i("NEQABTY", "Request Completed Successfully");

        Toast.makeText(context, response.PaymentRequestStatus.Name, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(PaymentException paymentException) {
//        Log.e("NEQABTY", paymentException.details.getMessage());


    }
}