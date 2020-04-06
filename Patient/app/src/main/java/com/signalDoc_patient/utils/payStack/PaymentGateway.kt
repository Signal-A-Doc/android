package com.signalDoc_patient.utils.payStack

import android.content.Context
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.signalDoc_patient.ui.activity.BaseActivity

class PaymentGateway {
    val callBack = CallBackPayment.getInstance()
    fun cardObject(cardNumber: String, expiryMonth: Int, expiryYear: Int, cvv: String): Card? {
        val card = Card(cardNumber, expiryMonth, expiryYear, cvv)
        return if (card.isValid) card else null
    }

    fun deductAmount(context: BaseActivity, charge: Charge, paymentDeduct: CallBackPayment.PaymentDeduct) {
        callBack.setListener(paymentDeduct)
        PaystackSdk.chargeCard(context, charge, object : Paystack.TransactionCallback {
            override fun onSuccess(transaction: Transaction?) {
                callBack.paymentDeduct.onSuccess(transaction)
            }

            override fun beforeValidate(transaction: Transaction?) {
                callBack.paymentDeduct.beforeValidate(transaction)
            }

            override fun onError(error: Throwable?, transaction: Transaction?) {
                callBack.paymentDeduct.onError(error, transaction)
            }
        })
    }

}