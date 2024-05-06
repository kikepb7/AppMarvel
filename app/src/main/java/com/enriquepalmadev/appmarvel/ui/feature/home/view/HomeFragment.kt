<<<<<<<< HEAD:ui-layer/src/main/java/com/enriquepalmadev/ui_layer/feature/comics/view/HomeFragment.kt
package com.enriquepalmadev.ui_layer.feature.comics.view
========
package com.enriquepalmadev.appmarvel.ui.feature.home.view
>>>>>>>> develop:app/src/main/java/com/enriquepalmadev/appmarvel/ui/feature/home/view/HomeFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<<< HEAD:ui-layer/src/main/java/com/enriquepalmadev/ui_layer/feature/comics/view/HomeFragment.kt
import com.enriquepalmadev.ui_layer.R
import com.enriquepalmadev.ui_layer.databinding.FragmentHomeBinding
import com.enriquepalmadev.ui_layer.feature.comics.view.utils.navigateTo
========
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.databinding.FragmentHomeBinding
import com.enriquepalmadev.appmarvel.ui.feature.home.view.extensions.navigateTo
>>>>>>>> develop:app/src/main/java/com/enriquepalmadev/appmarvel/ui/feature/home/view/HomeFragment.kt

class HomeFragment : Fragment() {

    // Binding to access to view objects on activity_main.xml
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Button Listener
        binding.apply {
            // Navigate through the path indicated in "main_graph.xml"
            buttonComics.navigateTo(R.id.action_homeFragment_to_comicsFragment)
            buttonCharacters.navigateTo(R.id.action_homeFragment_to_charactersFragment)
            buttonFilmsAndSeries.navigateTo(R.id.action_homeFragment_to_filmsAndSeriesFragment)
        }
        return binding.root
    }
}