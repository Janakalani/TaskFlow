package edu.uncc.assignment06.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymad.MainActivity
import com.example.mymad.databinding.FragmentAddTaskBinding
import com.example.mymad.models.Task

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private var name: String? = null
    private var priorityStr: String? = null
    private var category: String? = null
    private var priorityInt = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSelectPriority.setOnClickListener {
            mListener?.gotoSelectPriority()
        }

        binding.buttonSelectCategory.setOnClickListener {
            mListener?.gotoSelectCategory()
        }

        binding.buttonSubmit.setOnClickListener {
            priorityInt = 5
            name = binding.editTextName.text.toString()

            if (name.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "Enter Name!", Toast.LENGTH_SHORT).show()
            } else if (priorityStr == null) {
                Toast.makeText(requireActivity(), "Select Priority!", Toast.LENGTH_SHORT).show()
            } else if (category == null) {
                Toast.makeText(requireActivity(), "Select Category!", Toast.LENGTH_SHORT).show()
            } else {
                when (priorityStr) {
                    "Very Low" -> priorityInt = 1
                    "Low" -> priorityInt = 2
                    "Medium" -> priorityInt = 3
                    "High" -> priorityInt = 4
                }
                val task = Task(name!!, category!!, priorityStr!!, priorityInt)
                MainActivity.TAG?.let { tag ->
                    task.let {
                        Log.d(tag, "Submit Task: $it")
                    }
                }
                mListener?.submitTask(task)
            }
        }
    }

    // Interface
    private var mListener: AddTaskFragmentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as? AddTaskFragmentInterface
    }

    interface AddTaskFragmentInterface {
        fun gotoSelectPriority()
        fun gotoSelectCategory()
        fun submitTask(task: Task)
    }

    // Methods to set Values received from other fragments
    fun setPriorityStr(priority: String) {
        priorityStr = priority
    }

    fun setCategory(category: String) {
        this.category = category
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
