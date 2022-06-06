package com.example.travellorganizer.ui.items

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.travellorganizer.databinding.FragmentItemsBinding
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Items
import java.lang.Exception


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
        itemsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val addButton: ImageButton = binding.toAddItemButton

        addButton.setOnClickListener {
            val intent = Intent(context, AddItem::class.java)
            startActivity(intent)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}