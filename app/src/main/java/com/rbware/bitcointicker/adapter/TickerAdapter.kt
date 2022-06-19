package com.rbware.bitcointicker.adapter

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbware.bitcointicker.api.model.Currency
import com.rbware.bitcointicker.fragment.ARG_CURRENCY_NAME
import com.rbware.bitcointicker.fragment.ARG_CURRENCY_VALUE
import com.rbware.bitcointicker.fragment.TickerFragment

/**
 * Adapter for fragments
 */
class TickerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    var items: List<Currency>
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            mutableItems = value.toMutableList()
            notifyDataSetChanged()
        }
        get() = mutableItems

    private var mutableItems = mutableListOf<Currency>()

    init {
        val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {

            override fun onChanged() {

            }
        }

        registerAdapterDataObserver(adapterDataObserver)
    }

    override fun createFragment(position: Int): Fragment {
        return TickerFragment().apply {
            arguments = bundleOf(ARG_CURRENCY_NAME to items[position].symbol, ARG_CURRENCY_VALUE to items[position].last)
        }
    }

    /**
     * @see FragmentStateAdapter.getItemCount
     */
    override fun getItemCount(): Int = items.size
}