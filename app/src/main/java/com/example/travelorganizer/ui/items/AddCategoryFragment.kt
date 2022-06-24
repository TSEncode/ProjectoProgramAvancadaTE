package com.example.travelorganizer.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentAddCategoryBinding
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Category
import org.w3c.dom.Text
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCategoryFragment : Fragment() {
    private var _binding: FragmentAddCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemsViewModel =
            ViewModelProvider(this).get(ItemsViewModel::class.java)

        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_save
    }

    //Cria-se as rotas para os butoes do menu do topo
    // gere-se através do id do item que é clicado
    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.saveButton -> {
                insertCategory()
                return true
            }
            R.id.closeButton -> {
                returnToAddItems()
                return  true
            }
            else -> false
        }

    }

    fun returnToAddItems(){
        findNavController().navigate(AddCategoryFragmentDirections.actionNavigationAddCategoryFragmentToNavigationAddItemsFragment())
    }

    //Função que inserimos categorias
    fun insertCategory(){
        val name = binding.categoryNameValue.text.toString()
        val activity = activity as MainActivity

        val validated =  activity.validateFields(name,binding.categoryNameValue, getString(R.string.fill_the_name))

        val category = Category(name)

        //se os dados forem validados então guardam-se
        if(validated){
            //usa-se o contentResolver para inserir os dados, é passado os endereço do caminho do provider da categoria e os dados a inserir
            val url = requireActivity().contentResolver.insert(TravelContentProvider.CATEGORY_URL, category.toContentValues())

            if(url != null){
                Toast.makeText(requireContext(), getString(R.string.category_added), Toast.LENGTH_LONG).show()
                returnToAddItems()
            }else{
                Toast.makeText(requireContext(), getString(R.string.error_category), Toast.LENGTH_LONG).show()
            }
        }

    }



}