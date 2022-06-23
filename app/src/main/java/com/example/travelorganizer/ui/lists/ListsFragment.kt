package com.example.travelorganizer.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentListsBinding
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.adapters.ListAdapter

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
            findNavController().navigate(R.id.action_navigation_list_to_createListFragment)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu

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

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        //TODO
        return true
    }
}