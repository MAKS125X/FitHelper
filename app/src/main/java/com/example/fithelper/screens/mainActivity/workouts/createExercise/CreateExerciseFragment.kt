package com.example.fithelper.screens.mainActivity.workouts.createExercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentBottomSheetCreateExerciseBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.screens.shared.ExercisesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.IllegalArgumentException


class CreateExerciseFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetCreateExerciseBinding

    val vm: CreateExerciseViewModel by viewModels()
    val exercisesViewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetCreateExerciseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClicks()

        binding.exerciseNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })
    }

    private fun initObservers() {
        vm.numberOfApproaches.observe(activity as LifecycleOwner) { value ->
            binding.setsTV.text = "Подходов: $value"
        }

        vm.numberOfRepetitions.observe(activity as LifecycleOwner) { value ->
            binding.repsTV.text = "Повторений: $value"
        }

        vm.weight.observe(activity as LifecycleOwner) { value ->
            binding.weightTV.text = "Вес: $value"
        }
    }

    private fun initClicks() {
        val lowChangeValue = 1
        val fastChangeValue = 5

        binding.decreaseSetsButton.setOnClickListener {
            vm.changeNumberOfApproaches(-lowChangeValue)
        }

        binding.increaseSetsButton.setOnClickListener {
            vm.changeNumberOfApproaches(lowChangeValue)
        }

        binding.decreaseRepsButton.setOnClickListener {
            vm.changeNumberOfRepetitions(-lowChangeValue)
        }

        binding.increaseRepsButton.setOnClickListener {
            vm.changeNumberOfRepetitions(lowChangeValue)
        }

        binding.fastDecreaseRepsButton.setOnClickListener {
            vm.changeNumberOfRepetitions(-fastChangeValue)
        }

        binding.fastIncreaseRepsButton.setOnClickListener {
            vm.changeNumberOfRepetitions(fastChangeValue)
        }

        binding.decreaseWeightButton.setOnClickListener {
            vm.changeWeight(-lowChangeValue)
        }

        binding.increaseWeightButton.setOnClickListener {
            vm.changeWeight(lowChangeValue)
        }

        binding.fastDecreaseWeightButton.setOnClickListener {
            vm.changeWeight(-fastChangeValue)
        }

        binding.fastIncreaseWeightButton.setOnClickListener {
            vm.changeWeight(fastChangeValue)
        }

        binding.completeAddExerciseButton.setOnClickListener {
            try {
                exercisesViewModel.addExercise(
                    vm.name.value!!,
                    vm.numberOfApproaches.value!!,
                    vm.numberOfRepetitions.value!!,
                    vm.weight.value!!
                )
                (activity as MainActivity).navController.navigateUp()
            } catch (ex: IllegalArgumentException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateExerciseFragment()
    }
}

