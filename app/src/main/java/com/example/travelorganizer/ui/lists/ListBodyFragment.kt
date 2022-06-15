package com.example.travelorganizer.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        arguments?.let {
            id = it.getLong("list_id")
        }
        _binding = FragmentListBodyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val test = binding.teste


        test.setText(" O id é: ${id}")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}