package com.example.taskm6_2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.taskm6_2.model.Filter
import com.example.taskm6_2.model.Filter.ALL
import com.example.taskm6_2.model.Filter.COMPLETED
import com.example.taskm6_2.model.Filter.UNCOMPLETED
import com.example.taskm6_2.model.Task
import com.example.taskm6_2.room.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {

    private val filter = MutableLiveData(ALL)

    val taskList: LiveData<List<Task>> = filter.switchMap { filter: Filter ->
        when (filter) {
            ALL -> taskDao.getAll()
            COMPLETED -> taskDao.getCompleted()
            UNCOMPLETED -> taskDao.getUncompleted()
        }
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDao.delete(taskId = task.id)
    }

    fun completeTask(task: Task, isComplete: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val updated = task.copy(isCompleted = isComplete)
        taskDao.insert(updated)
    }

    fun filter(filter: Filter) {
        this.filter.postValue(filter)
    }
}