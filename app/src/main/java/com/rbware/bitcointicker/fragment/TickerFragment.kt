package com.rbware.bitcointicker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbware.bitcointicker.databinding.FragmentTickerBinding
import java.text.SimpleDateFormat
import java.util.*

const val ARG_CURRENCY_NAME = "currency_name"
const val ARG_CURRENCY_VALUE = "currency_value"


class TickerFragment : Fragment() {

    private var _binding: FragmentTickerBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyName = arguments?.getString(ARG_CURRENCY_NAME) ?: ""
        val currencyValue = arguments?.getDouble(ARG_CURRENCY_VALUE) ?: 0.0
        showInformation(currencyName = currencyName, currencyValue)
    }

    @SuppressLint("SimpleDateFormat") // this is only because we have a set date/time format given in the reqs
    private fun showInformation(currencyName: String, currencyValue: Double) {

        // This is currently independent of the actual timer on MainActivity.
        // They don't *need* to be linked, but i think they are supposed to be.
        // Updating an API the way I am right now and not tying this to DataBinding
        // is going to lead to future issues.
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy hh:mm:ss a")

        val dateTimeOfUpdate = simpleDateFormat.format(calendar.time)

        val title = "$dateTimeOfUpdate \n $currencyValue BTC/${currencyName}"
        binding.nameTv.text = title
    }
}