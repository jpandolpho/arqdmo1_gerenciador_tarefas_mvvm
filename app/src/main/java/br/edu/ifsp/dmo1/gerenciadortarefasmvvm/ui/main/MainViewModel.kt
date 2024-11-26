package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.dao.TaskDao
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model.Task

class MainViewModel:ViewModel() {
    private val dao = TaskDao

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    private val _insertedTask = MutableLiveData<Boolean>()
    val insertedTask : LiveData<Boolean> = _insertedTask

    private val _updateTask = MutableLiveData<Boolean>()
    val updateTask : LiveData<Boolean> = _updateTask

    init{
        mock()
        load()
    }

    fun insertTask(description: String) {
        dao.add(Task(description,false))
        _insertedTask.value = true
        load()
    }

    fun updateTask(position: Int){
        val task = dao.getAll()[position]
        task.isCompleted = !task.isCompleted
        _updateTask.value = true
        load()
    }

    private fun mock() {
        dao.add(Task("Arrumar a Cama", false))
        dao.add(Task("Retirar o lixo", false))
        dao.add(Task("Fazer trabalho de DMO1", true))
    }

    private fun load() {
        _tasks.value = dao.getAll()
    }
}