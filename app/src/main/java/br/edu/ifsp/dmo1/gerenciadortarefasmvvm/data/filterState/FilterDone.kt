package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState

object FilterDone:FilterState {
    override fun next():FilterState {
        return FilterAll
    }
}