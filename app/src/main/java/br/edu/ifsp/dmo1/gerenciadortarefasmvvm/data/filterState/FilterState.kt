package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState

interface FilterState {
    fun next(): FilterState
}