package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.filterState

object FilterPending:FilterState {
    override fun next():FilterState {
        return FilterDone
    }
}