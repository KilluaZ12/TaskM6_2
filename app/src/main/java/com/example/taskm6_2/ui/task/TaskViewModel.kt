package com.example.taskm6_2.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.taskm6_2.model.Filter
import com.example.taskm6_2.model.Task
import com.example.taskm6_2.room.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {

    private val _task = MutableLiveData<Task>()

    val task: LiveData<Task?> = _task.switchMap { taskDao.getTask(it.id) }

    suspend fun onSaveTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insert(task)
    }

    fun setTask(task: Task?) {
        task?.let {
            _task.postValue(it)
        }
    }
}