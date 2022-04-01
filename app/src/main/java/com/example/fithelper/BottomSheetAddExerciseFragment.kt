package com.example.fithelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.fithelper.databinding.FragmentBottomSheetAddExerciseBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetAddExerciseFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetAddExerciseBinding
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    var numberOfSets = 0
    var numberOfReps = 0
    var weight = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomSheetAddExerciseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.decreaseSetsButton.setOnClickListener {
            if (numberOfSets > 0) {
                numberOfSets--
                binding.setsTV.text = "Подходов: $numberOfSets"
            }
        }

        binding.increaseSetsButton.setOnClickListener {
            numberOfSets++
            binding.setsTV.text = "Подходов: $numberOfSets"
        }

        binding.fastDecreaseRepsButton.setOnClickListener {
            if (numberOfReps - 5 >= 0) {
                numberOfReps -= 5
                binding.repsTV.text = "Повторений: $numberOfReps"
            }
        }

        binding.decreaseRepsButton.setOnClickListener {
            if (numberOfReps > 0) {
                numberOfReps--
                binding.repsTV.text = "Повторений: $numberOfReps"
            }
        }

        binding.increaseRepsButton.setOnClickListener {
            numberOfReps++
            binding.repsTV.text = "Повторений: $numberOfReps"
        }

        binding.fastIncreaseRepsButton.setOnClickListener {
            numberOfReps += 5
            binding.repsTV.text = "Повторений: $numberOfReps"
        }

        binding.fastDecreaseWeightButton.setOnClickListener {
            if (weight - 5 >= 0) {
                weight -= 5
                binding.weightTV.text = "Вес: $weight"
            }
        }

        binding.decreaseWeightButton.setOnClickListener {
            if (weight > 0) {
                weight--
                binding.weightTV.text = "Вес: $weight"
            }
        }

        binding.increaseWeightButton.setOnClickListener {
            weight++
            binding.weightTV.text = "Вес: $weight"
        }

        binding.fastIncreaseWeightButton.setOnClickListener {
            weight += 5
            binding.weightTV.text = "Вес: $weight"
        }

        binding.completeAddExerciseButton.setOnClickListener {
            when {
                binding.exerciseNameET.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Введите название упражнения",
                        Toast.LENGTH_LONG
                    ).show()
                }
                numberOfSets == 0 -> {
                    Toast.makeText(
                        requireContext(),
                        "Число подходов должно быть положительным!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                numberOfReps == 0 -> {
                    Toast.makeText(
                        requireContext(),
                        "Число повторений должно быть положительным!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    workoutViewModel.exercise.value = Exercise(
                        binding.exerciseNameET.text.toString(),
                        numberOfSets, numberOfReps, weight, isComplete = false
                    )
                    numberOfSets = 0
                    numberOfReps = 0
                    weight = 0
                    binding.exerciseNameET.text.clear()
                    this.dismiss()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomSheetAddExerciseFragment()
    }
}