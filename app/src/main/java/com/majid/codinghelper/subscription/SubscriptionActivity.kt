package com.majid.codinghelper.subscription

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.majid.codinghelper.R
import com.majid.codinghelper.databinding.ActivitySubscriptionBinding
import com.majid.codinghelper.subscription.adapters.SubscriptionAdapter
import com.majid.codinghelper.subscription.models.SubscriptionItem

class SubscriptionActivity : AppCompatActivity() {

    lateinit var  binding: ActivitySubscriptionBinding
    private val viewModel: SubscriptionViewModel by viewModels()

    private lateinit var subscriptionAdapter: SubscriptionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.subscribe(this)

        viewModel.subscriptionStatus.observe(this, { status ->
            // Update UI based on the subscription status
            if (status) {
                // User has an active subscription
            } else {
                // User does not have an active subscription
            }
        })


        initSubscriptionAdapter()
    }

    private fun initSubscriptionAdapter() {
        val subscriptionRecyclerView = binding.subscriptionRecyclerView

        // Create a list of subscription items
        val subscriptions = listOf(
            SubscriptionItem("Subscription 1", "$9.99"),
            SubscriptionItem("Subscription 2", "$19.99"),
            SubscriptionItem("Subscription 3", "$29.99")
            // Add more subscription items as needed
        )

        // Initialize the subscription adapter
        subscriptionAdapter = SubscriptionAdapter(subscriptions)

        // Set the adapter to the RecyclerView
        subscriptionRecyclerView.adapter = subscriptionAdapter
    }
}
