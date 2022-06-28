package com.example.travelorganizer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.travelorganizer.databinding.ActivityMainBinding
import com.example.travelorganizer.ui.items.AddCategoryFragment
import com.example.travelorganizer.ui.items.AddItemsFragment
import com.example.travelorganizer.ui.items.ItemsFragment
import com.example.travelorganizer.ui.lists.ItemToListFragment
import com.example.travelorganizer.ui.lists.ListBodyFragment
import com.example.travelorganizer.ui.lists.ListsFragment
import com.example.travelorganizer.ui.travels.TravelsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
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

    private var menu : Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_travell, R.id.navigation_list, R.id.navigation_item
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(idMenuTop,menu)

        this.menu = menu
        return true;
    }

    //função para gerir campos, retorna true se forem validados
    fun validateFields(

        fieldString : String ? = null,
        text : TextView,
        msg : String,
        spinner : Long = -1 ,
        isSpinner : Boolean = false
    ) : Boolean{
        if(!isSpinner){
            if(fieldString!!.isBlank()){
                text.error = msg
                text.requestFocus()
                return false
            }
        }


        if(isSpinner){
            if(spinner == Spinner.INVALID_ROW_ID){
                text.error
                text.requestFocus()
                return false
            }
        }
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //vamos fazer o nosso encaminhamento dependendo do botão selecionado
        val optionProcessed : Boolean

        if(fragment is ListsFragment){
            optionProcessed = (fragment as ListsFragment).handlerOptionProcessed(item)
        }else if(fragment is ItemsFragment){
            optionProcessed = (fragment as ItemsFragment).handlerOptionProcessed(item)
        }else if(fragment is AddItemsFragment){
            optionProcessed = (fragment as AddItemsFragment).handlerOptionProcessed(item)
        }else if(fragment is AddCategoryFragment){
            optionProcessed = (fragment as AddCategoryFragment).handlerOptionProcessed(item)
        }else if(fragment is ListBodyFragment){
            optionProcessed = (fragment as ListBodyFragment).handlerOptionProcessed(item)
        }else if(fragment is TravelsFragment){
            optionProcessed = (fragment as TravelsFragment).handlerOptionProcessed(item)
        }else if(fragment is ItemToListFragment){
            optionProcessed = (fragment as ItemToListFragment).handlerOptionProcessed(item)
    }


        return super.onOptionsItemSelected(item)
    }

    //função que mostra os menus de apagar e editar

    fun changeMenuOps(changeOps : Boolean){
       menu!!.findItem(R.id.deleteButton).isVisible = changeOps
        menu!!.findItem(R.id.editButton).isVisible = changeOps
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}