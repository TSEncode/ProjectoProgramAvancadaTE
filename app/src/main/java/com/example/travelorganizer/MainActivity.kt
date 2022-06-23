package com.example.travelorganizer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.travelorganizer.databinding.ActivityMainBinding
import com.example.travelorganizer.ui.lists.ListsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var idMenuTop = R.menu.bottom_nav_menu
        get() = field
        set(value){
                if(value != field){
                    field = value
                    invalidateOptionsMenu()
                }
        }

    var fragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_travell, R.id.navigation_list, R.id.navigation_item
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(idMenuTop,menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //vamos fazer o nosso encaminhamento dependendo do botão selecionado
        val optionProcessed : Boolean

        if(fragment is ListsFragment){
            optionProcessed = (fragment as ListsFragment).handlerOptionProcessed(item)
        }


        return super.onOptionsItemSelected(item)
    }
}