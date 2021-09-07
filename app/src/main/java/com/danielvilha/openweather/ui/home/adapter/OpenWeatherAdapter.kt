package com.danielvilha.openweather.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.danielvilha.openweather.databinding.ViewWeatherBinding
import com.danielvilha.openweather.network.WeatherItem

/**
 * Created by danielvilha on 07/09/21
 * https://github.com/danielvilha
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class OpenWeatherAdapter: ListAdapter<WeatherItem, OpenWeatherAdapter.OpenWeatherViewHolder>(DiffCallback) {

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [WeatherItem]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem.dt == newItem.dt
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenWeatherViewHolder {
        return OpenWeatherViewHolder(ViewWeatherBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: OpenWeatherViewHolder, position: Int) {
        val property = getItem(position)
        holder.bind(property)
    }

    class OpenWeatherViewHolder(private var binding: ViewWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherItem) {
            binding.textDate.text = item.dt_txt

            binding.textTemperature.text = item.main?.temp.toString()
            binding.textTemperatureMin.text = item.main?.temp_min.toString()
            binding.textTemperatureMax.text = item.main?.temp_max.toString()

            binding.textPressure.text = item.main?.pressure.toString()
            binding.textSeaLevel.text = item.main?.sea_level.toString()
            binding.textGrndLevel.text = item.main?.grnd_level.toString()

            binding.textHumidity.text = item.main?.humidity.toString()
            binding.textTempKf.text = item.main?.temp_kf.toString()

            binding.textMainDescription.text = "${item.weather?.get(0)?.main}, ${item.weather?.get(0)?.description}"
            binding.textSpeedDeg.text = "${item.wind?.speed} - ${item.wind?.deg} - ${item.wind?.gust}"
        }
    }
}