package com.danielvilha.openweather.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.danielvilha.openweather.R
import com.danielvilha.openweather.databinding.FragmentAboutBinding
import com.danielvilha.openweather.databinding.FragmentHomeBinding
import com.danielvilha.openweather.ui.home.HomeViewModel
import com.danielvilha.openweather.ui.home.adapter.OpenWeatherAdapter

/**
 * Created by danielvilha on 06/09/21
 * https://github.com/danielvilha
 */
class AboutFragment : Fragment() {

    // Binding object instance corresponding to the fragment_about.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentAboutBinding? = null

    private val aboutViewModel: AboutViewModel by lazy {
        ViewModelProvider(this).get(AboutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentAboutBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // Assign the view model to a property in the binding class
            viewModel = aboutViewModel
        }
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}