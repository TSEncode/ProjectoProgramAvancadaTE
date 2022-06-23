package com.example.travelorganizer.ui.items

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentItemsBinding
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.ui.items.adapters.ItemsAdapter


class ItemsFragment : Fragment() {

    private var _binding: FragmentItemsBinding? = null

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

        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val addButton: ImageButton = binding.toAddItemButton

        /*val items = Items(requireContext()).getAll()

        val recycler = binding.itemsReciclerView


        recycler.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)
            adapter = ItemsAdapter(items)


        }

        addButton.setOnClickListener {
           findNavController(this).navigate(R.id.navigation_addItemsFragment)
        }*/


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}