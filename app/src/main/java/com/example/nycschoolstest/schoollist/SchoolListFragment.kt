package com.example.nycschoolstest

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolstest.databinding.FragmentItemListBinding
import com.example.nycschoolstest.databinding.ItemListContentBinding
import com.example.nycschoolstest.repository.Repository
import com.example.nycschoolstest.repository.models.NycSchool

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        viewModel = ItemViewModelProvider(repo = Repository()).create(ItemViewModel::class.java)

        viewModel.getSchools()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.itemList
        val onClickListener = View.OnClickListener { itemView ->
            val itemId = itemView.tag as NycSchool
            val bundle = Bundle()
            bundle.putString(
                ItemDetailFragment.ARG_ITEM_ID,
                itemId.dbn
            )
            itemView.findNavController().navigate(R.id.show_item_detail, bundle)
        }

        viewModel.schoolsLiveData.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(recyclerView, onClickListener)
        })
    }

    private fun showToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener,
    ) {
        viewModel.schoolsLiveData.value?.let {
            recyclerView.adapter = SimpleItemRecyclerViewAdapter(
                it,
                onClickListener,
            )
        }
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: List<NycSchool>,
        private val onClickListener: View.OnClickListener
        ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.school_name
            holder.contentView.text = item.school_accessibility_description

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)

                setOnLongClickListener { v ->
                    // Setting the item id as the clip data so that the drop target is able to
                    // identify the id of the content
                    val clipItem = ClipData.Item(item.building_code)
                    val dragData = ClipData(
                        v.tag as? CharSequence,
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        clipItem
                    )

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    } else {
                        v.startDrag(
                            dragData,
                            View.DragShadowBuilder(v),
                            null,
                            0
                        )
                    }
                }
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}