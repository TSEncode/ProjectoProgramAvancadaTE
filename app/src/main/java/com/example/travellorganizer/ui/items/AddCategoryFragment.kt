package com.example.travellorganizer.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentAddCategoryBinding
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.models.Category
import java.lang.Exception
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

        val addCategory =  binding.addCategoryButton

        addCategory.setOnClickListener{
            val categoryNameEditText = binding.categoryNameValue

            val categoryName = categoryNameEditText.text.toString()

            Category(requireContext(),categoryName).insertCategory()
            Toast.makeText(context, getString(R.string.category_added), Toast.LENGTH_SHORT).show()

            categoryNameEditText.setText("")
        }

        return root
    }


}