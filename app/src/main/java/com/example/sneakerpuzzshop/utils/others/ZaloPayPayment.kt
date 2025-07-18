package com.example.sneakerpuzzshop.utils.others

import android.app.Activity
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.sneakerpuzzshop.utils.zalopay.Api.CreateOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

sealed class PaymentResult {
    object Success : PaymentResult()
    object Canceled : PaymentResult()
    data class Error(val error: ZaloPayError?) : PaymentResult()
}

fun startPayment(amount: String, activity: Activity, callback: (PaymentResult) -> Unit) {
    // Cho phép gọi API sync trong thread hiện tại (demo)
    StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())

    // Khởi tạo SDK (gọi một lần cho cả app, đặt ở Application càng tốt)
    ZaloPaySDK.init(2553, Environment.SANDBOX)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val orderAPI = CreateOrder()
            val data = orderAPI.createOrder(amount)
            if (data.getString("return_code") == "1") {
                val token = data.getString("zp_trans_token")

                // Chuyển về Main thread để gọi SDK
                withContext(Dispatchers.Main) {
                    ZaloPaySDK.getInstance().payOrder(
                        activity,
                        token,
                        "demozpdk://app",
                        object : PayOrderListener {
                            override fun onPaymentSucceeded(
                                transactionId: String?,
                                transToken: String?,
                                appTransId: String?
                            ) {
                                callback(PaymentResult.Success)
                            }

                            override fun onPaymentCanceled(
                                transToken: String?,
                                appTransId: String?
                            ) {
                                callback(PaymentResult.Canceled)
                            }

                            override fun onPaymentError(
                                zaloPayError: ZaloPayError?,
                                transToken: String?,
                                appTransId: String?
                            ) {
                                callback(PaymentResult.Error(zaloPayError))
                            }
                        }
                    )
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback(PaymentResult.Error(null))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                callback(PaymentResult.Error(null))
            }
        }
    }
}