package com.example.taskm6_2.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskm6_2.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1")
    fun getCompleted(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    fun getUncompleted(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTask(taskId: Long): LiveData<Task?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun delete(taskId: Long)
}