package com.enriquepalmadev.appmarvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.enriquepalmadev.appmarvel.databinding.ActivityMainBinding
import com.enriquepalmadev.appmarvel.view.CharactersFragment
import com.enriquepalmadev.appmarvel.view.ComicsFragment
import com.enriquepalmadev.appmarvel.view.FilmsSeriesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonComics.setOnClickListener { replaceFragment(ComicsFragment()) }
        binding.buttonCharacters.setOnClickListener { replaceFragment(CharactersFragment()) }
        binding.buttonFilmsAndSeries.setOnClickListener { replaceFragment(FilmsSeriesFragment()) }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}