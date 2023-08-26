package com.example.taskm6_2.ui.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskm6_2.databinding.ItemTaskBinding
import com.example.taskm6_2.model.Task

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(diffCallBack) {

    var onDelete: ((Task) -> Unit)? = null
    var onEdit: ((Task) -> Unit)? = null
    var onComplete: ((task: Task, isComplete: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Task) = with(binding) {
            checkBox.isChecked = task.isCompleted
            tvTitle.text = task.title
            tvTitle.setTextColor(if (task.isCompleted) Color.GREEN else Color.RED)
            tvDesc.text = task.description
            itemView.setOnLongClickListener {
                onDelete?.invoke(task)
                true
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onComplete?.invoke(task, isChecked)
            }
            ivEdit.setOnClickListener {
                onEdit?.invoke(task)
            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

        }
    }
}