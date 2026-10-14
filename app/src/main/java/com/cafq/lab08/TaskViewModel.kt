package com.cafq.lab08

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Ejercicio 1 (función 3): filtros para la lista de tareas
enum class TaskFilter(val label: String) {
    ALL("Todas"),
    PENDING("Pendientes"),
    COMPLETED("Completadas")
}

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    // Estado con TODAS las tareas guardadas en Room
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // Estado del filtro seleccionado
    private val _filter = MutableStateFlow(TaskFilter.ALL)
    val filter: StateFlow<TaskFilter> = _filter

    // Estado derivado: lista ya filtrada que consume la UI (UDF: la UI solo observa)
    val visibleTasks: StateFlow<List<Task>> = combine(_tasks, _filter) { lista, filtro ->
        when (filtro) {
            TaskFilter.ALL -> lista
            TaskFilter.PENDING -> lista.filter { !it.isCompleted }
            TaskFilter.COMPLETED -> lista.filter { it.isCompleted }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch { _tasks.value = dao.getAllTasks() }
    }

    fun setFilter(nuevoFiltro: TaskFilter) {
        _filter.value = nuevoFiltro
    }

    fun addTask(description: String) {
        viewModelScope.launch {
            dao.insertTask(Task(description = description))
            _tasks.value = dao.getAllTasks()
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            dao.updateTask(task.copy(isCompleted = !task.isCompleted))
            _tasks.value = dao.getAllTasks()
        }
    }

    // Ejercicio 1 (función 2): eliminar una sola tarea
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
            _tasks.value = dao.getAllTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            _tasks.value = emptyList()
        }
    }
}
