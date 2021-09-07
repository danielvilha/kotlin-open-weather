package com.danielvilha.openweather.ui.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielvilha.openweather.network.WeatherItem
import java.text.SimpleDateFormat

/**
 * Created by danielvilha on 07/09/21
 * https://github.com/danielvilha
 */

/**
 * When there is no Fallen Meteors property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<WeatherItem>?) {
    val adapter = recyclerView.adapter as OpenWeatherAdapter
    adapter.submitList(data)
}

//@BindingAdapter("dateFormat")
//fun bindDateFormat(date: String?) {
//    SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date)
//}