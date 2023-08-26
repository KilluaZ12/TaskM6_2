package com.example.taskm6_2.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskm6_2.databinding.FragmentTaskBinding
import com.example.taskm6_2.model.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val TAG: String = "TaskFragment"
    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by viewModels()

    private val safeArgs: TaskFragmentArgs by navArgs()
    private val task: Task? by lazy { safeArgs.task }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        setupView()
        setupObservers()
        return binding.root
    }

    private fun setupView() = with(binding) {
        Log.d(TAG, "setupView: ")
        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val task = task?.copy(title = title, description = desc) ?: Task(title = title, description = desc, isCompleted = false)
            lifecycleScope.launch {
                viewModel.onSaveTask(task)
                findNavController().navigateUp()
            }
        }
    }

    private fun setupObservers() = with(binding) {
        viewModel.setTask(task)
        viewModel.task.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            etTitle.setText(it.title)
            etDesc.setText(it.description)
        }
    }
}