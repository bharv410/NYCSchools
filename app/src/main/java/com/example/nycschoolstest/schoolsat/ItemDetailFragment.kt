package com.example.nycschoolstest

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import com.example.nycschoolstest.databinding.FragmentItemDetailBinding
import com.example.nycschoolstest.repository.Repository


class ItemDetailFragment : Fragment() {

    private var itemId: String? = null
    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ItemDetailViewModelProvider(repo = Repository()).create(ItemDetailViewModel::class.java)

        arguments?.let {
            if (it.containsKey(SCHOOL_ID)) {
                itemId = it.getString(SCHOOL_ID)
                itemId?.let { dbn ->
                    viewModel.getSats(dbn)
                }
            }
        }
        viewModel.satsLiveData.observe(this, Observer { sat ->
            sat?.let {

                        showToast("got it ${sat.sat_math_avg_score}")
                itemDetailTextView.text = "SAT MATH = ${sat.sat_math_avg_score}"

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.itemDetail

        updateContent()
//        rootView.setOnDragListener(dragListener)

        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = itemId

        // Show the placeholder content as text in a TextView.
        itemId?.let {
            itemDetailTextView.text = itemId
        }
    }

    companion object {
        const val SCHOOL_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun showToast(string: String){
        Toast.makeText(requireContext(),string,LENGTH_LONG).show()
    }
}