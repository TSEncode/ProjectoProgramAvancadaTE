package com.example.travelorganizer.ui.lists

import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentCreateListBinding
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.travels.TravelsViewModel
import java.net.URI

/**
 * A simple [Fragment] subclass.
 * Use the [CreateListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateListFragment : Fragment() {
    private var _binding: FragmentCreateListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(TravelsViewModel::class.java)

        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_save

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToList(id: Long){
        val action = CreateListFragmentDirections.actionNavigationCreateListFragmentToNavigationListBodyFragment(id)
        findNavController().navigate(action)
    }
    fun inserList(){
        val activity = activity as MainActivity

        val name = binding.listNameValue.text.toString()
        val descript = binding.descriptionListValue.text.toString()

        val nameValidated = activity.validateFields( name,binding.listNameValue, getString(R.string.fill_the_name))

        val list = Lists(
            name,
            descript
        )

        if(nameValidated){
            val url = requireActivity().contentResolver.insert(TravelContentProvider.LIST_URL, list.toContentValues())

            if(url != null){
                Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_LONG).show()
                //vai-se buscar o ultimo caminho do uri gerado, este é o id
                val id : Long = url.lastPathSegment!!.toLong()

                goToList(id)

            }else{
                Toast.makeText(requireContext(), getString(R.string.error_item), Toast.LENGTH_LONG).show()
            }
        }

    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.saveButton -> {
                inserList()
                return true
            }
            else -> false
        }
    }

    companion object{
        const val ID_LOADER_ITEMS = 0;
    }
}
