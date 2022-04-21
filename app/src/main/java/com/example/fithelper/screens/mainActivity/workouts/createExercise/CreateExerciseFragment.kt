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
import com.example.fithelper.databinding.FragmentBottomSheetCreateExerciseBinding
import com.example.fithelper.screens.shared.ExercisesViewModel
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
        binding.decreaseSetsButton.setOnClickListener {
            vm.decreaseNumberOfApproaches()
        }

        binding.increaseSetsButton.setOnClickListener {
            vm.increaseNumberOfApproaches()
        }

        binding.fastDecreaseRepsButton.setOnClickListener {
            vm.fastDecreaseNumberOfRepetitions()
        }

        binding.decreaseRepsButton.setOnClickListener {
            vm.decreaseNumberOfRepetitions()
        }

        binding.increaseRepsButton.setOnClickListener {
            vm.increaseNumberOfRepetitions()
        }

        binding.fastIncreaseRepsButton.setOnClickListener {
            vm.fastIncreaseNumberOfRepetitions()
        }

        binding.fastDecreaseWeightButton.setOnClickListener {
            vm.fastDecreaseWeight()
        }

        binding.decreaseWeightButton.setOnClickListener {
            vm.decreaseWeight()
        }

        binding.increaseWeightButton.setOnClickListener {
            vm.increaseWeight()
        }

        binding.fastIncreaseWeightButton.setOnClickListener {
            vm.fastIncreaseWeight()
        }

        binding.completeAddExerciseButton.setOnClickListener {
            try {
                exercisesViewModel.addExercise(
                    vm.name.value!!,
                    vm.numberOfApproaches.value!!,
                    vm.numberOfRepetitions.value!!,
                    vm.weight.value!!
                )
                // todo: переписать на граф
                this.dismiss()
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

