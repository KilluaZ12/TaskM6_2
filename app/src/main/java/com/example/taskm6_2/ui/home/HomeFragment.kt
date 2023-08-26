package com.example.taskm6_2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taskm6_2.R
import com.example.taskm6_2.databinding.FragmentHomeBinding
import com.example.taskm6_2.model.Filter
import com.example.taskm6_2.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val TAG: String = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val taskAdapter = TaskAdapter()
    private val filterAdapter: ArrayAdapter<String> by lazy { createFilterAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupFilter()
        setupObservers()
    }

    private fun setupViews() = with(binding) {
        Log.d(TAG, "setupViews: ")
        recyclerView.adapter = taskAdapter
        taskAdapter.onEdit = {
            val action = HomeFragmentDirections.actionAdd(it)
            findNavController().navigate(action)
        }
        taskAdapter.onDelete = {
            showDeleteDialog(it)
        }
        taskAdapter.onComplete = { task, isComplete ->
            viewModel.completeTask(task, isComplete)
        }
        fabCreate.setOnClickListener {
            Log.d(TAG, "setupViews: action add")
            val action = HomeFragmentDirections.actionAdd(null)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        viewModel.taskList.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }
    }

    private fun setupFilter() = with(binding) {
        Log.d(TAG, "setupFilter: adapter: ${filterAdapter.count}")
        filter.setText(Filter.ALL.name, false)
        filter.setOnItemClickListener { parent, view, position, id ->
            val filter = when (position) {
                0 -> Filter.ALL
                1 -> Filter.COMPLETED
                2 -> Filter.UNCOMPLETED
                else -> return@setOnItemClickListener
            }
            viewModel.filter(filter)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.filter.setAdapter(filterAdapter)
    }

    private fun showDeleteDialog(task: Task) {
        //TODO: Show dialog and when confirm delete
        viewModel.deleteTask(task)
    }

    private fun createFilterAdapter(): ArrayAdapter<String> {
        val filters = resources.getStringArray(R.array.filters)
        return ArrayAdapter(requireContext(), R.layout.item_filter, filters)
    }
}