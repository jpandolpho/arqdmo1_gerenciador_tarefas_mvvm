package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.dao.TaskDao
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState.FilterAll
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState.FilterDone
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState.FilterPending
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState.FilterState
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model.Task
import java.util.stream.Collectors

class MainViewModel:ViewModel() {
    private val dao = TaskDao

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    private val _insertedTask = MutableLiveData<Boolean>()
    val insertedTask : LiveData<Boolean> = _insertedTask

    private val _updateTask = MutableLiveData<Boolean>()
    val updateTask : LiveData<Boolean> = _updateTask

    private val _filterState = MutableLiveData<FilterState>()
    val filterState : LiveData<FilterState> = _filterState

    init{
        _filterState.value = FilterAll
        mock()
        load()
    }

    fun insertTask(description: String) {
        dao.add(Task(description,false))
        _insertedTask.value = true
        load()
    }

    fun updateTask(position: Int){
        //Necessário para tratar da atualização da lista quando um filtro esta sendo aplicado.
        val task = if(_filterState.value is FilterDone){
            dao.getAll()[position+dao.getAll().stream().filter{ t -> !t.isCompleted}.collect(
                Collectors.toList()).size]
        }else{
            dao.getAll()[position]
        }
        task.isCompleted = !task.isCompleted
        dao.updateList()
        _updateTask.value = true
        load()
    }

    private fun mock() {
        dao.add(Task("Arrumar a Cama", false))
        dao.add(Task("Retirar o lixo", false))
        dao.add(Task("Fazer trabalho de DMO1", true))
    }

    private fun load() {
        _tasks.value = when (_filterState.value) {
            is FilterAll -> dao.getAll()
            is FilterPending -> dao.getAll().stream().filter{ t -> !t.isCompleted}.collect(
                Collectors.toList())
            else -> dao.getAll().stream().filter{ t -> t.isCompleted}.collect(
                Collectors.toList())
        }
    }

    fun applyFilter() {
        //Apesar da recomendação de não se utilizar !!, aqui temos certeza que sempre haverá um valor
        //válido para o filterState, já que ele é iniciado no init.
        _filterState.value = _filterState.value!!.next()
        load()
    }
}