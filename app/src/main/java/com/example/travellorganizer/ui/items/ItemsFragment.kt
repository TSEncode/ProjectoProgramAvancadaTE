package com.example.travellorganizer.ui.items

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellorganizer.MainActivity
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentItemsBinding
import com.example.travellorganizer.models.Items
import com.example.travellorganizer.ui.items.adapters.ItemsAdapter


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

        val textView: TextView = binding.addItems
       /* itemsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val addButton: ImageButton = binding.toAddItemButton

        val items = Items(requireContext()).getAll()

        val recycler = binding.itemsReciclerView


        recycler.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)
            adapter = ItemsAdapter(items)
        }

        addButton.setOnClickListener {
           findNavController(this).navigate(R.id.navigation_addItemsFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}