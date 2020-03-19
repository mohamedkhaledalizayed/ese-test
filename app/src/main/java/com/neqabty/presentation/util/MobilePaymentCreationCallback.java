package com.neqabty.presentation.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.efinance.mobilepaymentsdk.PaymentCreationCallback;
import com.efinance.mobilepaymentsdk.PaymentCreationResponse;
import com.efinance.mobilepaymentsdk.PaymentException;
import com.neqabty.MainActivity;

public class MobilePaymentCreationCallback  implements PaymentCreationCallback {

    private Context context;
    private String paymentMethod;
    
    public MobilePaymentCreationCallback(Context context, String paymentMethod){
        this.context = context;
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public void onSuccess(PaymentCreationResponse response) {
        Log.i("NEQABTY", "Request Completed Successfully");

        Toast.makeText(context, "Payment Created Successfully", Toast.LENGTH_LONG).show();

        if (paymentMethod.equals("Card")) {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("senderRequestNumber", response.OriginalSenderRequestNumber);
            intent.putExtra("cardRequestNumber", response.CardRequestNumber);
            intent.putExtra("sessionID", response.SessionId);
            intent.putExtra("amount", Double.toString(response.TotalAuthorizationAmount));

            context.startActivity(intent);
        }
        else if(paymentMethod.equals("Channel") ||
                paymentMethod.equals("Mobile Wallet") ||
                paymentMethod.equals("Meeza")) {

            Toast.makeText(context, "Use this number as a reference: " + response.CardRequestNumber, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("senderRequestNumber", response.OriginalSenderRequestNumber);

            context.startActivity(intent);
        }

    }

    @Override
    public void onError(PaymentException paymentException) {
        Log.e("NEQABTY", paymentException.details.getMessage());
    }
}