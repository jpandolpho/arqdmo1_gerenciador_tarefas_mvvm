package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.R
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.databinding.DialogNewTaskBinding
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.adapter.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        configListview()
        configClickListener()
        configObservers()
    }

    private fun configClickListener() {
        binding.buttonAddTask.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_new_task, null)
            val bindingDialog = DialogNewTaskBinding.bind(dialogView)

            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Adicionar Tarefa")
                .setPositiveButton("Salvar", DialogInterface.OnClickListener { dialogInterface, i ->
                    val desc = bindingDialog.editDescription.text.toString()
                    viewModel.insertTask(desc)
                    dialogInterface.dismiss()
                })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            builder.create().show()
        }
    }

    private fun configListview() {
        adapter = TaskAdapter(this, mutableListOf())
        binding.listTasks.adapter = adapter
    }

    private fun configObservers() {
        viewModel.tasks.observe(this, Observer {
            adapter.updateTasks(it)
        })
        viewModel.insertedTask.observe(this,Observer{
            val str = if(it){
                "Inserido com sucesso"
            }else{
                "Erro ao inserir"
            }
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
        })
    }
}