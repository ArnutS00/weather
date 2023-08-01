package com.example.weatherforecast.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.base.BaseFragment
import com.example.weatherforecast.base.binding.viewBinding
import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.extension.hideKeyboardFrom
import com.example.weatherforecast.base.extension.invisible
import com.example.weatherforecast.base.extension.loadImageUrl
import com.example.weatherforecast.base.extension.observe
import com.example.weatherforecast.base.extension.show
import com.example.weatherforecast.base.model.WeatherModel
import com.example.weatherforecast.base.model.error
import com.example.weatherforecast.base.model.success
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING
import com.example.weatherforecast.base.provider.VariablesProvider.NA
import com.example.weatherforecast.databinding.FragmentHomeBinding
import com.example.weatherforecast.ui.home.viewmodel.HomeSharedViewModel
import com.example.weatherforecast.ui.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), TabLayout.OnTabSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val homeSharedViewModel by activityViewModels<HomeSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserve()
    }

    private fun initView() = with(binding) {
        homeViewModel.loadWeatherData("Bangkok", getUnitsType())
    }

    private fun initObserve() {
        observe(homeViewModel.weatherDataLiveData) {
            it.success { data ->
                setWeatherView(data)
                homeSharedViewModel.setWeatherData(data)
            }
            it.error {
                setWeatherView(null)
            }
        }
    }

    private fun setWeatherView(weatherData: WeatherModel?) {
        binding.apply {
            weatherData?.let { data ->
                tvCityName.text = data.cityName
                tvTemperature.text = data.temp
                tvUnit.text = getUnitsType().tempUnit
                tvHumidityValue.text = data.humidity
                tvPressureValue.text = data.pressure
                tvTemperatureDesc.text = data.weatherDesc
                tvWindSpeedValue.text = data.windSpeed
                tvCloudValue.text = data.cloud
                val minMaxTemp = "${data.tempMin}/${data.tempMax} ${getUnitsType().tempUnit}"
                tvTemperatureMaxMin.text = minMaxTemp
                ivTemperatureStatus.loadImageUrl(data.weatherIconURL)
                ivTemperatureStatus.show()
                tvUnit.show()
                tvTime.text = data.time
            } ?: run {
                tvCityName.text = NA
                tvTemperature.text = NA
                tvUnit.invisible()
                tvHumidityValue.text = NA
                tvPressureValue.text = NA
                tvTemperatureDesc.text = NA
                tvWindSpeedValue.text = NA
                tvCloudValue.text = NA
                tvTemperatureMaxMin.text = NA
                tvTime.text = NA
                ivTemperatureStatus.invisible()
            }

        }
    }

    private fun initListener() = with(binding) {
        ivSearch.setOnClickListener {
            val cityName = edtCityName.text.toString()
            if (cityName.isNotEmpty()){
                homeViewModel.loadWeatherData(edtCityName.text.toString(), getUnitsType())
                ivSearch.hideKeyboardFrom(context)
                edtCityName.text?.clear()
            }
        }
        cvCurrentTemperature.setOnClickListener {
            if (tvCityName.text.toString() != NA){
                homeSharedViewModel.setUnits(getUnitsType())
                findNavController().navigate(R.id.action_homeFragment_to_forecastFragment)
            }
        }
        tabUnits.addOnTabSelectedListener(this@HomeFragment)
    }

    private fun getUnitsType(): UnitsType {
        binding.apply {
            return when (tabUnits.selectedTabPosition) {
                0 -> {
                    UnitsType.CELSIUS
                }

                1 -> {
                    UnitsType.FAHRENHEIT
                }

                else -> {
                    UnitsType.UNKNOW
                }
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                val cityName = binding.tvCityName.text.toString()
                homeViewModel.loadWeatherData(
                    if (cityName == NA) DEFAULT_STRING else cityName,
                    UnitsType.CELSIUS
                )
            }

            1 -> {
                val cityName = binding.tvCityName.text.toString()
                homeViewModel.loadWeatherData(
                    if (cityName == NA) DEFAULT_STRING else cityName,
                    UnitsType.FAHRENHEIT
                )
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        //Nothing
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //Nothing
    }
}