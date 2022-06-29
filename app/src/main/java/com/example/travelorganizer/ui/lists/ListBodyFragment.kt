package com.example.travelorganizer.ui.lists

import android.net.Uri
import android.os.Bundle
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

import com.example.travelorganizer.databinding.FragmentListBodyBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ListBodyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListBodyFragment : Fragment() {
    private var _binding: FragmentListBodyBinding? = null
    private var id : Long? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var uri : Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        _binding = FragmentListBodyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            val teste = binding.teste

            teste.text = ListBodyFragmentArgs.fromBundle(arguments!!).listUri.toString()

            val activity = activity as MainActivity

            activity.fragment = this
            activity.idMenuTop = R.menu.top_nav_save


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.closeButton -> {
                findNavController().navigate(ListBodyFragmentDirections.actionNavigationListBodyFragmentToNavigationList(-1))
                return true
            }
            else -> false
        }
    }
}