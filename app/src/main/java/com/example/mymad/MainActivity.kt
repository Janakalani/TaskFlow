package com.example.mymad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.mymad.fragments.SelectCategoryFragment
import com.example.mymad.fragments.SelectPriorityFragment
import com.example.mymad.fragments.TaskDetailsFragment
import com.example.mymad.fragments.TasksFragment
import com.example.mymad.models.Data
import com.example.mymad.models.Task
import edu.uncc.assignment06.fragments.AddTaskFragment

class MainActivity : AppCompatActivity(),
    TasksFragment.TasksFragmentInterface,
    TaskDetailsFragment.TaskDetailsFragmentInterface,
    AddTaskFragment.AddTaskFragmentInterface,
    SelectPriorityFragment.SelectPriorityFragmentInterface,
    SelectCategoryFragment.SelectCategoryFragmentInterface {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var fragmentManager: FragmentManager
    private val mTasks: ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTasks.addAll(Data.sampleTestTasks) // adding for testing
        fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .add(R.id.rootView, TasksFragment(), "TasksFragment")
            .commit()
    }

    // Implement methods from TasksFragmentInterface
    override fun getAllTasks(): ArrayList<Task> = mTasks

    override fun gotoAddTask() {
        fragmentManager.beginTransaction()
            .replace(R.id.rootView, AddTaskFragment(), "AddTaskFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun gotoTaskDetails(task: Task) {
        fragmentManager.beginTransaction()
            .replace(R.id.rootView, TaskDetailsFragment.newInstance(task), "TaskDetailsFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun clearAllTask() {
        mTasks.clear()
    }

    override fun deleteItem(task: Task) {
        mTasks.remove(task)
    }

    // Implement methods from TaskDetailsFragmentInterface
    override fun goBack() {
        fragmentManager.popBackStack()
    }

    override fun deleteTask(task: Task) {
        mTasks.remove(task)
        fragmentManager.popBackStack()
    }

    // Implement methods from AddTaskFragmentInterface
    override fun gotoSelectPriority() {
        fragmentManager.beginTransaction()
            .replace(R.id.rootView, SelectPriorityFragment(), "SelectPriorityFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun gotoSelectCategory() {
        fragmentManager.beginTransaction()
            .replace(R.id.rootView, SelectCategoryFragment(), "SelectCategoryFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun submitTask(task: Task) {
        mTasks.add(task)
        fragmentManager.popBackStack()
    }

    // Implement methods from SelectPriorityFragmentInterface
    override fun sendPriority(priority: String) {
        val fragment = fragmentManager.findFragmentByTag("AddTaskFragment") as? AddTaskFragment
        fragment?.setPriorityStr(priority)
        fragmentManager.popBackStack()
    }

    override fun cancelPriority() {
        fragmentManager.popBackStack()
    }

    // Implement methods from SelectCategoryFragmentInterface
    override fun sendCategory(category: String) {
        val fragment = fragmentManager.findFragmentByTag("AddTaskFragment") as? AddTaskFragment
        fragment?.setCategory(category)
        fragmentManager.popBackStack()
    }

    override fun cancelCategory() {
        fragmentManager.popBackStack()
    }
}
