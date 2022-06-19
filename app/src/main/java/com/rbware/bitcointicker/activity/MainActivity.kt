package com.rbware.bitcointicker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.internal.LinkedTreeMap
import com.rbware.bitcointicker.adapter.TickerAdapter
import com.rbware.bitcointicker.api.model.Currency
import com.rbware.bitcointicker.databinding.ActivityMainBinding
import com.rbware.bitcointicker.networking.ApiProvider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TickerAdapter
    private lateinit var binding: ActivityMainBinding

    var currencyList: MutableList<Currency> = mutableListOf()
    lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD
        // https://blockchain.info/ticker

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainHandler = Handler(Looper.getMainLooper())

        getBTCPrices()
        binding.mainPager.setCurrentItem(0, false)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }

    private fun getBTCPrices() {

        // launching a new coroutine
        CoroutineScope(Dispatchers.IO).launch {

            val btcPrices = ApiProvider.retrofitService.getPrices()

            withContext(Dispatchers.Main) {
                if (btcPrices.isSuccessful) {
                    currencyList.clear()
                    val listOfResponses = btcPrices.body() as LinkedTreeMap<String, Currency>
                    listOfResponses.forEach {
                        currencyList.add(it.value)
                    }
                    updateArrayAdapter(binding.mainPager.currentItem)
                }
            }
        }
    }

    private fun updateArrayAdapter(positionToDisplay: Int) {
        adapter = TickerAdapter(this)
            .apply { items = currencyList }

        binding.mainPager.adapter = adapter
        binding.mainPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        })

        binding.mainPager.setPageTransformer(MarginPageTransformer(300))
        binding.mainPager.setCurrentItem(positionToDisplay, false)
    }


    private val updateTextTask = object : Runnable {
        override fun run() {
            getBTCPrices()
            mainHandler.postDelayed(this, 1000)
        }
    }
}