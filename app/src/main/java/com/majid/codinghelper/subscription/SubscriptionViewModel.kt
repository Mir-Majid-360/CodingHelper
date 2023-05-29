package com.majid.codinghelper.subscription

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.*
import com.majid.codinghelper.app.App

class SubscriptionViewModel : ViewModel(), PurchasesUpdatedListener {

    private val _subscriptionStatus = MutableLiveData<Boolean>()
    val subscriptionStatus: MutableLiveData<Boolean> get() = _subscriptionStatus

    private var billingClient: BillingClient


    init {


        billingClient = BillingClient.newBuilder(App.context)
            .setListener(this)
            .enablePendingPurchases()
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                    // Billing client is ready
//                    // Perform further operations, such as querying available products or checking subscription status
//
//                    // Example: Query available products
//                    val skuList = listOf("your_subscription_sku_1", "your_subscription_sku_2")
//                    val params = SkuDetailsParams.newBuilder()
//                        .setType(BillingClient.SkuType.SUBS)
//                        .setSkusList(skuList)
//                        .build()
//
//                    billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
//                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
//                            // Process the list of available products/skus
//                            for (skuDetails in skuDetailsList) {
//                                // Access sku details (e.g., title, price) and update the UI
//                            }
//                        } else {
//                            // Handle query failure
//                        }
//                    }
//
//                    // Example: Check user's subscription status
//                    val purchaseResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
//                    val purchasedSubscriptions = purchaseResult.purchasesList
//                    val hasActiveSubscription = !purchasedSubscriptions.isNullOrEmpty()
//                    _subscriptionStatus.value = hasActiveSubscription
                }
            }


            override fun onBillingServiceDisconnected() {
                // Handle billing client disconnect
            }
        })

    }

    fun subscribe(activity: Activity) {
        val skuList = listOf("your_subscription_sku")
        val params = SkuDetailsParams.newBuilder()
            .setType(BillingClient.SkuType.SUBS)
            .setSkusList(skuList)
            .build()

        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                val skuDetails = skuDetailsList.firstOrNull()
                if (skuDetails != null) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build()

                    billingClient.launchBillingFlow(activity, flowParams)
                }
            }
        }
    }


    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Process the purchased items
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle user cancellation
        } else {
            // Handle other errors
        }
    }


    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // Process the purchase and grant the subscription to the user
            _subscriptionStatus.value = true

            // Save the purchase details or acknowledge the purchase
            val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

            billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Purchase acknowledged successfully
                    // You can save the purchase details or perform any necessary actions
                    savePurchaseDetails(purchase)
                } else {
                    // Handle acknowledgment failure
                    // Display an error message or perform appropriate error handling
                }
            }

            // ...
        }
    }



    private fun savePurchaseDetails(purchase: Purchase) {
        // Save purchase details to a local database or shared preferences
        // Example: Save purchase token and subscription details
        val purchaseToken = purchase.purchaseToken
        val subscriptionDetails = purchase.purchaseTime.toString()

        // Implement your storage mechanism here
    }


    fun cancelSubscription() {
        val purchaseToken = ""// Obtain the purchase token of the subscription to be canceled
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()

        billingClient.acknowledgePurchase(params) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Subscription cancellation acknowledged
                _subscriptionStatus.value = false
            } else {
                // Handle cancellation acknowledgement failure
            }
        }
    }
}
