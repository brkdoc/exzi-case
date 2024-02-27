package com.burakdemir.ExziCase.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakdemir.ExziCase.viewmodel.OrderBookViewModel
import com.burakdemir.ExziCase.R
import com.burakdemir.ExziCase.adapter.OpenOrderViewPagerAdapter
import com.burakdemir.ExziCase.adapter.OrderBookAdapter
import com.burakdemir.ExziCase.databinding.FragmentSpotBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class SpotFragment : Fragment() {
    private var _binding: FragmentSpotBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderBookViewModel by viewModels()
    private lateinit var buyOrderBookAdapter: OrderBookAdapter
    private lateinit var sellOrderBookAdapter: OrderBookAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
       viewModel.fetchOrderBook()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buyOrderBookAdapter = OrderBookAdapter(mutableListOf(),"buy")
        sellOrderBookAdapter = OrderBookAdapter(mutableListOf(),"sell")

        binding.buttonBuy.setOnClickListener {
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
            binding.buttonSell.setBackgroundColor(ContextCompat.getColor(requireContext(),
                R.color.black
            ))
            binding.buttonOrder.setBackgroundColor(ContextCompat.getColor(requireContext(),
                R.color.green
            ))
            binding.buttonOrder.text = "Buy"
        }

        binding.buttonSell.setOnClickListener {
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            binding.buttonBuy.setBackgroundColor(ContextCompat.getColor(requireContext(),
                R.color.black
            ))
            binding.buttonOrder.setBackgroundColor(ContextCompat.getColor(requireContext(),
                R.color.red
            ))
            binding.buttonOrder.text = "Sell"
        }

        binding.buttonPlus.setOnClickListener {
            var amount = binding.editTextNumber.text.toString().toDoubleOrNull()
            if (amount != null) {
                amount += 0.1
            }
            amount?.let { binding.editTextNumber.setText(String.format("%.1f", amount)) }
        }

        binding.buttonMinus.setOnClickListener {
            var amount = binding.editTextNumber.text.toString().toDoubleOrNull()
            if (amount != null) {
                amount -= 0.1
            }
            amount?.let { binding.editTextNumber.setText(String.format("%.1f", amount)) }
        }

        binding.editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty()) {
                    try {
                        val value = text.toDouble()
                        val result = value / 30
                        val formattedResult = NumberFormat.getCurrencyInstance(Locale.US).format(result)
                        binding.textViewCurrency.text = "=$formattedResult"
                    } catch (e: NumberFormatException) {
                        binding.textViewCurrency.text = "Invalid input"
                    }
                } else {
                    binding.textViewCurrency.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        val spinner: Spinner = binding.spinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.trade_dropdown_items,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinnerSelector: Spinner = binding.spinnerSelector

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.quantity_selector_dropdown_items,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSelector.adapter = adapter
        }

        val adapter = OpenOrderViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Open Order"
                1 -> "Order History"
                else -> "Funds"
            }
        }.attach()

        binding.recyclerViewOrderBookBuy.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOrderBookBuy.adapter = buyOrderBookAdapter

        binding.recyclerViewOrderBookSell.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOrderBookSell.adapter = sellOrderBookAdapter

        viewModel.orderBookStream.observe(viewLifecycleOwner) { orderBook ->
            buyOrderBookAdapter.updateOrders(orderBook.buy.toMutableList())
        }

        viewModel.orderBookStream.observe(viewLifecycleOwner) { orderBook ->
            sellOrderBookAdapter.updateOrders(orderBook.sell.toMutableList())
        }

        viewModel.orderBookResponse.observe(viewLifecycleOwner) { orderBook ->
            buyOrderBookAdapter.updateOrders(orderBook.buy.sortedBy { it.price }.reversed().toMutableList())
        }

        viewModel.orderBookResponse.observe(viewLifecycleOwner) { orderBook ->
            sellOrderBookAdapter.updateOrders(orderBook.sell.sortedByDescending { it.price }.reversed().toMutableList())
        }

        binding.imageViewCandle.setOnClickListener {
            findNavController().navigate(TradeFragmentDirections.actionTradeFragmentToChartFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}