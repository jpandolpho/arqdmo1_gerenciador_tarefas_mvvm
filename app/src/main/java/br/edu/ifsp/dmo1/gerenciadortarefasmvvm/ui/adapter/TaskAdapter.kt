package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.R
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model.Task
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.databinding.TasklistItemBinding
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.listener.TaskClickListener

class TaskAdapter(
    context: Context,
    private var myTasks : List<Task>,
    private val clickListener: TaskClickListener
) : ArrayAdapter<Task>(context, R.layout.tasklist_item, myTasks){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding : TasklistItemBinding
        if (convertView == null) {
            binding = TasklistItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            binding.root.tag = binding
        } else {
            binding = convertView.tag as TasklistItemBinding
        }

        val task = getItem(position)
        if (task != null) {
            binding.textTaskDescription.text = task.description
            if (task.isCompleted) {
                binding.imageDone.setColorFilter(ContextCompat.getColor(context, R.color.green))
            } else {
                binding.imageDone.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
            binding.imageDone.setOnClickListener { 
                clickListener.clickDone(position)
            }
        }

        return binding.root
    }

    fun updateTasks(newTasks: List<Task>){
        myTasks = newTasks
        clear()
        addAll(myTasks)
        notifyDataSetChanged()
    }
}