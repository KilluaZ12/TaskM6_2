package com.example.taskm6_2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true)
                val id: Long = 0,
                val title: String?,
                val description: String?,
                val isCompleted: Boolean) : Parcelable