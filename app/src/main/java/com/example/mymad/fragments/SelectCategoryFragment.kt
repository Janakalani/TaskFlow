package com.example.mymad.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymad.MainActivity
import com.example.mymad.R
import com.example.mymad.databinding.FragmentSelectCategoryBinding
import com.example.mymad.models.Data

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: SelectCategoryFragmentInterface
    private lateinit var mCategories: Array<String>
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCategories = Data.categories

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CategoryAdapter()
        binding.recyclerView.adapter = adapter

        binding.buttonCancel.setOnClickListener {
            mListener.cancelCategory()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as? SelectCategoryFragmentInterface
            ?: throw ClassCastException("$context must implement SelectCategoryFragmentInterface")
    }

    interface SelectCategoryFragmentInterface {
        fun sendCategory(category: String)
        fun cancelCategory()
    }

    inner class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.selection_list_item, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = mCategories[position]
            holder.textViewCategory.text = category

            holder.itemView.setOnClickListener {
                mListener.sendCategory(category)
                MainActivity.TAG?.let { tag ->
                    Log.d(tag, "Selected Category: $category")
                }
            }
        }

        override fun getItemCount(): Int {
            return mCategories.size
        }

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewCategory: TextView = itemView.findViewById(R.id.textViewItem)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
