package com.burakdemir.ExziCase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakdemir.ExziCase.adapter.OpenOrderAdapter
import com.burakdemir.ExziCase.databinding.FragmentOpenOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenOrderFragment : Fragment() {

    private var _binding: FragmentOpenOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = OpenOrderAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}