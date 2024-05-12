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
import com.example.mymad.databinding.FragmentSelectPriorityBinding
import com.example.mymad.models.Data

class SelectPriorityFragment : Fragment() {

    private var _binding: FragmentSelectPriorityBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: SelectPriorityFragmentInterface
    private lateinit var mPriorities: Array<String>
    private lateinit var adapter: PriorityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectPriorityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPriorities = Data.priorities

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = PriorityAdapter()
        binding.recyclerView.adapter = adapter

        binding.buttonCancel.setOnClickListener {
            mListener.cancelPriority()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as? SelectPriorityFragmentInterface
            ?: throw ClassCastException("$context must implement SelectPriorityFragmentInterface")
    }

    interface SelectPriorityFragmentInterface {
        fun sendPriority(priority: String)
        fun cancelPriority()
    }

    inner class PriorityAdapter : RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.selection_list_item, parent, false)
            return PriorityViewHolder(view)
        }

        override fun onBindViewHolder(holder: PriorityViewHolder, position: Int) {
            val priorityStr = mPriorities[position]
            holder.textViewPriorityStr.text = priorityStr

            holder.itemView.setOnClickListener {
                mListener.sendPriority(priorityStr)
                MainActivity.TAG?.let { tag ->
                    Log.d(tag, "Selected Priority: $priorityStr")
                }
            }
        }

        override fun getItemCount(): Int {
            return mPriorities.size
        }

        inner class PriorityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewPriorityStr: TextView = itemView.findViewById(R.id.textViewItem)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
