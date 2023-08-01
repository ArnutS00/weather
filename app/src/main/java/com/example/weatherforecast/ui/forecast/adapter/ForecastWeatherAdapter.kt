package com.example.weatherforecast.ui.forecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.extension.dismiss
import com.example.weatherforecast.base.extension.loadImageUrl
import com.example.weatherforecast.base.extension.show
import com.example.weatherforecast.base.model.ForecastWeatherModel
import com.example.weatherforecast.databinding.ItemForecastWeatherBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ForecastWeatherAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder>() {

    lateinit var listForecastWeather: List<ForecastWeatherModel>
    lateinit var unitsType: UnitsType


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemForecastWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun getItemCount() = listForecastWeather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listForecastWeather[position])
    }


    inner class ViewHolder(
        val binding: ItemForecastWeatherBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecastData: ForecastWeatherModel) = with(binding) {
            if (!forecastData.date.isNullOrEmpty()){
                llDate.show()
                tvDate.text = forecastData.date
            }else{
                llDate.dismiss()
            }

            tvTime.text = forecastData.time
            val minMaxTemp = "${forecastData.tempMin}/${forecastData.tempMax} ${unitsType.tempUnit}"
            tvTemperatureMaxMin.text = minMaxTemp
            tvWeatherDesc.text = forecastData.weatherDesc
            ivTemperatureStatus.loadImageUrl(forecastData.weatherIconURL)
        }
    }


}