package com.example.weatherforecast.ui.forecast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.weatherforecast.R
import com.example.weatherforecast.base.BaseFragment
import com.example.weatherforecast.base.extension.dismiss
import com.example.weatherforecast.base.extension.init
import com.example.weatherforecast.base.extension.invisible
import com.example.weatherforecast.base.extension.loadImageUrl
import com.example.weatherforecast.base.extension.observe
import com.example.weatherforecast.base.extension.onBackPress
import com.example.weatherforecast.base.extension.show
import com.example.weatherforecast.base.model.ForecastWeatherModel
import com.example.weatherforecast.base.model.WeatherModel
import com.example.weatherforecast.base.model.error
import com.example.weatherforecast.base.model.success
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING
import com.example.weatherforecast.base.provider.VariablesProvider.NA
import com.example.weatherforecast.databinding.FragmentWeatherForecastBinding
import com.example.weatherforecast.ui.forecast.adapter.ForecastWeatherAdapter
import com.example.weatherforecast.ui.forecast.viewmodel.ForecastViewModel
import com.example.weatherforecast.ui.home.viewmodel.HomeSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : BaseFragment(R.layout.fragment_weather_forecast) {

    private lateinit var binding: FragmentWeatherForecastBinding
    private val forecastViewModel by viewModels<ForecastViewModel>()
    private val homeSharedViewModel by activityViewModels<HomeSharedViewModel>()

    @Inject
    lateinit var forecastAdapter: ForecastWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserve()
        forecastViewModel.loadForecastData(
            homeSharedViewModel.getWeatherData()?.cityName ?: DEFAULT_STRING,
            homeSharedViewModel.getUnitsType()
        )
    }

    private fun initView() = with(binding) {
        rvForecastWeather.init()
        setWeatherView(homeSharedViewModel.getWeatherData())
        toolbar.setTitle(homeSharedViewModel.getWeatherData()?.cityName ?: DEFAULT_STRING)
    }

    private fun initObserve() {
        observe(forecastViewModel.forecastLiveData) {
            it.success { data ->
                setViewForecastList(data)
            }
            it.error {
                setViewNoData()
            }
        }
    }

    private fun setViewNoData() {
        binding.apply {
            rvForecastWeather.dismiss()
            llNoData.show()
        }
    }

    private fun setViewForecastList(data: List<ForecastWeatherModel>) {
        forecastAdapter.listForecastWeather = data
        forecastAdapter.unitsType = homeSharedViewModel.getUnitsType()
        binding.apply {
            llNoData.dismiss()
            rvForecastWeather.show()
            rvForecastWeather.adapter = forecastAdapter
        }
    }

    private fun initListener() = with(binding) {
        toolbar.back.setOnClickListener {
            activity.onBackPress()
        }
    }

    private fun setWeatherView(weatherData: WeatherModel?) {
        binding.apply {
            weatherData?.let { data ->
                tvTemperature.text = data.temp
                tvUnit.text = homeSharedViewModel.getUnitsType().tempUnit
                tvTemperatureDesc.text = data.weatherDesc
                ivTemperatureStatus.loadImageUrl(data.weatherIconURL)
                ivTemperatureStatus.show()
            } ?: run {
                tvTemperature.text = NA
                tvUnit.invisible()
                tvTemperatureDesc.text = NA
                ivTemperatureStatus.invisible()
            }

        }
    }

}