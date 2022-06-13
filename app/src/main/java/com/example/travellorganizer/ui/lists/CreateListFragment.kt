package com.example.travellorganizer.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentCreateListBinding
import com.example.travellorganizer.models.Lists
import com.example.travellorganizer.ui.travels.TravelsViewModel

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


        val addList = binding.addListButton

        addList.setOnClickListener {
            val name = binding.listNameValue.text.toString()
            val descrip = binding.descriptionListValue.text.toString()

            Lists(requireContext(), name, descrip).insertList()

            binding.listNameValue.setText("")
            binding.descriptionListValue.setText("")

            Toast.makeText(context, getString(R.string.list_sucess), Toast.LENGTH_SHORT)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}