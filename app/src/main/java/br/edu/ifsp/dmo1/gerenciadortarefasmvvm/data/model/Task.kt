package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model

class Task(var description: String, var isCompleted: Boolean):Comparable<Task> {

    private companion object {
        var lastId: Long = 1L
    }

    var id: Long = 0L

    init {
        id = lastId
        lastId += 1L
    }

    override fun compareTo(other: Task): Int {
        if(this.isCompleted.compareTo(other.isCompleted)==0){
            return this.description.compareTo(other.description)
        }
        return this.isCompleted.compareTo(other.isCompleted)
    }
}