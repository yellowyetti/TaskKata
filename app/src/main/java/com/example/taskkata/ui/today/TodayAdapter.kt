package com.example.taskkata.ui.today

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskkata.database.Task
import com.example.taskkata.databinding.RecyclerViewItemBinding

class TodayAdapter(val clickListener: TaskListener): ListAdapter<Task, TodayAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflate list layout and return viewholder
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }



    class ViewHolder private constructor(val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        val card: CardView = binding.recyclerCard
        private val checkBox: CheckBox = binding.cardCheckbox
        private val textView: TextView = binding.cardTaskDescription

        fun bind(
            item: Task,
            clickListener: TaskListener
        ) {
            binding.task = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = RecyclerViewItemBinding.inflate(inflater, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class TaskDiffCallback() : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}

class TaskListener(val clickListener: (taskId: Int) -> Unit, val checkBoxListener: (taskId: Int) -> Unit) {
    fun onClick(task: Task) = clickListener(task.taskId)
    fun onCheckBoxClicked(task: Task) = checkBoxListener(task.taskId)
}