package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState

object FilterAll:FilterState {
    override fun next():FilterState {
        return FilterPending
    }
}