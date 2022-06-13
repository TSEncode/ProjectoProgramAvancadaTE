package com.example.travellorganizer.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellorganizer.R

import com.example.travellorganizer.databinding.FragmentListBodyBinding
import com.example.travellorganizer.models.Items
import com.example.travellorganizer.ui.items.ItemsViewModel
import com.example.travellorganizer.ui.items.adapters.ItemsAdapter



/**
 * A simple [Fragment] subclass.
 * Use the [ListBodyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListBodyFragment : Fragment() {
    private var _binding: FragmentListBodyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        _binding = FragmentListBodyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val test = binding.teste

       listsViewModel.id.observe(viewLifecycleOwner, Observer{ id ->
            binding.teste.setText(id.toString())
        } )


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}