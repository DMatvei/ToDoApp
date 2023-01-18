package com.example.tasksappbymatt.ui.tasks

import androidx.lifecycle.ViewModel
import com.example.tasksappbymatt.data.TaskDao
import javax.inject.Inject


class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

}