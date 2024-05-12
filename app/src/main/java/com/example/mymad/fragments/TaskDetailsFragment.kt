package com.example.mymad.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymad.databinding.FragmentTaskDetailsBinding
import com.example.mymad.models.Task

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task

    companion object {
        private const val ARG_PARAM1 = "TASK_KEY"

        fun newInstance(task: Task): TaskDetailsFragment {
            val fragment = TaskDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, task)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            task = it.getSerializable(ARG_PARAM1) as Task
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewName.text = task.name
        binding.textViewCategory.text = task.category
        binding.textViewPriority.text = task.priorityStr

        binding.buttonBack.setOnClickListener {
            mListener.goBack()
        }

        binding.imageViewDelete.setOnClickListener {
            mListener.deleteTask(task)
            Toast.makeText(requireActivity(), "${task.name} Deleted!", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var mListener: TaskDetailsFragmentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as TaskDetailsFragmentInterface
    }

    interface TaskDetailsFragmentInterface {
        fun goBack()
        fun deleteTask(task: Task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
