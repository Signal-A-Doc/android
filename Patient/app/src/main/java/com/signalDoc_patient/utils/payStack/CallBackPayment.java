package com.signalDoc_patient.utils.payStack;

import co.paystack.android.Transaction;

public class CallBackPayment {

    static private CallBackPayment payment = new CallBackPayment();
    public PaymentDeduct paymentDeduct;

    static public CallBackPayment getInstance() {
        return payment;
    }

    public void setListener(PaymentDeduct paymentDeduct) {
        this.paymentDeduct = paymentDeduct;
    }


    public interface PaymentDeduct {
        public void onSuccess(Transaction transaction);

        public void beforeValidate(Transaction transaction);

        public void onError(Throwable error, Transaction transaction);
    }

}
