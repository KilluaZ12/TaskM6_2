package com.example.taskm6_2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskm6_2.model.Task
import com.example.taskm6_2.room.TaskDao

@Database(entities = [Task::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}