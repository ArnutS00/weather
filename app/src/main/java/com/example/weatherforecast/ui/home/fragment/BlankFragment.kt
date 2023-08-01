package com.example.weatherforecast.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherforecast.R
import com.example.weatherforecast.base.BaseFragment
import com.example.weatherforecast.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : BaseFragment(R.layout.fragment_blank) {

    private lateinit var binding : FragmentBlankBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initObserve()

    }

    private fun initView() = with(binding) {

    }

    private fun initListener() = with(binding) {

    }

    private fun initObserve() = with(binding) {

    }
}