package com.example.tasksappbymatt.ui.addedittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tasksappbymatt.data.Task
import com.example.tasksappbymatt.data.TaskDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddEditTaskViewModel @Inject constructor (
    private val taskDao: TaskDao,
    private val state: SavedStateHandle,
): ViewModel(){

    val task = state.get<Task>("task")


    var taskName = state.get<String>("taskName") ?: task?.name ?: ""
        set(value) {
            field = value
            state.set("taskName", value)
        }


    var taskImportance = state.get<Boolean>("taskImportance") ?: task?.important ?: false
        set(value) {
            field = value
            state.set("taskImportance", value)
        }


}