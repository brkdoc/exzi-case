package com.burakdemir.ExziCase.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.burakdemir.ExziCase.databinding.FragmentChartBinding
import com.burakdemir.ExziCase.databinding.FragmentSpotBinding
import com.burakdemir.ExziCase.viewmodel.ChartViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChartViewModel by viewModels()
    private lateinit var buyOrderBookAdapter: OrderBookAdapter
    private lateinit var sellOrderBookAdapter: OrderBookAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchOrderBook()
        viewModel.fetchTicker()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buyOrderBookAdapter = OrderBookAdapter(mutableListOf(),"buy")
        sellOrderBookAdapter = OrderBookAdapter(mutableListOf(),"sell")

        val spinnerSelector: Spinner = binding.spinnerSelector

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.quantity_selector_dropdown_items,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSelector.adapter = adapter
        }

        binding.recyclerViewOrderBookBuy.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOrderBookBuy.adapter = buyOrderBookAdapter

        binding.recyclerViewOrderBookSell.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOrderBookSell.adapter = sellOrderBookAdapter

        viewModel.orderBookResponse.observe(viewLifecycleOwner) { orderBook ->
            buyOrderBookAdapter.updateOrders(orderBook.buy.sortedBy { it.price }.reversed().toMutableList())
        }

        viewModel.orderBookResponse.observe(viewLifecycleOwner) { orderBook ->
            sellOrderBookAdapter.updateOrders(orderBook.sell.sortedByDescending { it.price }.reversed().toMutableList())
        }

        viewModel.tickerResponse.observe(viewLifecycleOwner) { tickerResponse ->
           println("hrllo "+tickerResponse.data.find { it.name == "BTCUSDT"}?.name)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}