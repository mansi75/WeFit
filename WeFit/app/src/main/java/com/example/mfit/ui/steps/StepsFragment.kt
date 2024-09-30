package com.example.mfit.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mfit.databinding.FragmentStepsBinding
import com.example.mfit.ui.MainViewModel
import com.example.mfit.utils.Helper

class StepsFragment : Fragment() {

    private var _binding: FragmentStepsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val sharedVM: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        sharedVM.steps.observe(viewLifecycleOwner) {
            it.let {
                binding.stepsTv.text = it.toString()
                binding.distanceTv.text = Helper.getDistanceCovered(it)
                binding.caloriesTv.text = Helper.getCalories(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}