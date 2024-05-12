package com.example.mymad.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymad.MainActivity
import com.example.mymad.R
import com.example.mymad.databinding.FragmentTaskBinding
import com.example.mymad.models.Task

class TasksFragment : Fragment() {

    private var mTasks = ArrayList<Task>()

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TasksAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        mListener.getAllTasks()?.let { tasks ->
            mTasks.addAll(tasks)
        }

        binding.buttonAddNew.setOnClickListener {
            mListener.gotoAddTask()
        }

        binding.buttonClearAll.setOnClickListener {
            mListener.clearAllTask()
            adapter.notifyDataSetChanged()
            Toast.makeText(activity, "List Cleared!", Toast.LENGTH_SHORT).show()
        }

        binding.imageViewSortAsc.setOnClickListener {
            sortList(1)
            binding.textViewSortIndicator.text = "Sort By Priority (ASC)"
        }

        binding.imageViewSortDesc.setOnClickListener {
            sortList(-1)
            binding.textViewSortIndicator.text = "Sort By Priority (DESC)"
        }
    }

    private fun sortList(orderType: Int) {
        if (mTasks.size <= 1) {
            Toast.makeText(activity, "Not enough items to sort!", Toast.LENGTH_SHORT).show()
        } else {
            mTasks.sortWith(Comparator { task1, task2 ->
                (orderType * (task1.priority - task2.priority)).toInt()
            })
            adapter.notifyDataSetChanged()
        }
    }

    private lateinit var mListener: TasksFragmentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as TasksFragmentInterface
    }

    interface TasksFragmentInterface {
        fun getAllTasks(): ArrayList<Task>?
        fun gotoAddTask()
        fun gotoTaskDetails(task: Task)
        fun clearAllTask()
        fun deleteItem(task: Task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

        inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewName: TextView = itemView.findViewById(R.id.textViewName)
            val textViewCategory: TextView = itemView.findViewById(R.id.textViewCategory)
            val textViewPriorityStr: TextView = itemView.findViewById(R.id.textViewPriority)
            val imageViewDeleteItem: ImageView = itemView.findViewById(R.id.imageViewDeleteItem)

            fun setupUI(task: Task) {
                textViewName.text = task.name
                textViewCategory.text = task.category
                textViewPriorityStr.text = task.priorityStr
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
            return TasksViewHolder(view)
        }

        override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
            val task = mTasks[position]

            holder.setupUI(task)

            holder.itemView.setOnClickListener {
                Log.d(MainActivity.TAG, "onClick: Position $position - ${task.name}")
                mListener.gotoTaskDetails(task)
            }

            holder.imageViewDeleteItem.setOnClickListener {
                mListener.deleteItem(task)
                notifyDataSetChanged()
                Toast.makeText(activity, "${task.name} Deleted!", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int {
            return mTasks.size
        }
    }
}
