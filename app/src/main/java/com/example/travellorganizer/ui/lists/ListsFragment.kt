package com.example.travellorganizer.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentListsBinding
import com.example.travellorganizer.models.Lists
import com.example.travellorganizer.ui.lists.adapters.ListAdapter

class ListsFragment : Fragment(), GetAdapterData {

    private var _binding: FragmentListsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        _binding = FragmentListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val addButton = binding.newListbutton


        val recycler = binding.listRecylerView

        val lists = Lists(requireContext()).getAll()

        val listAdapter = ListAdapter(lists, this)

        recycler.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        addButton.setOnClickListener{
            findNavController().navigate(R.id.navigation_createListFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getId(id: Long) {
        val bundle = Bundle()
        bundle.putLong("list_id", id)

        findNavController().navigate(R.id.navigation_listBodyFragment, bundle)
    }
}