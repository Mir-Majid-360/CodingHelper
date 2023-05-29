package com.majid.codinghelper.subscription.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majid.codinghelper.databinding.ItemSubscriptionBinding
import com.majid.codinghelper.subscription.models.SubscriptionItem

class SubscriptionAdapter(private val subscriptions: List<SubscriptionItem>) :
    RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubscriptionBinding.inflate(inflater, parent, false)
        return SubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        val subscription = subscriptions[position]
        holder.bind(subscription)
    }

    override fun getItemCount(): Int {
        return subscriptions.size
    }

    inner class SubscriptionViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subscription: SubscriptionItem) {
            // Bind the subscription data to the view holder
            binding.tvTitle.text = subscription.title
            binding.tvMessage.text = subscription.price
            // ... Bind other subscription data as needed
        }
    }
}
