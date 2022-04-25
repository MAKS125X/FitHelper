package com.example.fithelper.screens.common

import android.content.Context
import com.example.fithelper.databinding.DialogCreateExerciseBinding
import com.example.fithelper.extensions.add
import com.example.fithelper.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateExerciseDialog(context: Context, listener: OnExerciseCreatedListener)
    : BottomSheetDialog(context) {
    private val binding: DialogCreateExerciseBinding =
        DialogCreateExerciseBinding.inflate(layoutInflater)

    private var numberOfApproaches = 1
    private var numberOfRepetitions = 1
    private var weight = 0

    constructor(context: Context, listener: (Exercise) -> Unit)
            : this(context, object : OnExerciseCreatedListener {
        override fun onExerciseCreated(exercise: Exercise) {
            listener(exercise)
        }
    })

    init {
        binding.completeAddExerciseButton.setOnClickListener {
            val name = binding.exerciseNameET.text.toString()

            listener.onExerciseCreated(
                Exercise(
                    name,
                    numberOfApproaches,
                    numberOfRepetitions,
                    weight
                )
            )

            this.dismiss()
        }

        val lowChangeValue = 1
        val fastChangeValue = 5
        binding.decreaseSetsButton.setOnClickListener {
            numberOfApproaches = numberOfApproaches.add(-lowChangeValue, lowerBound = 1)
            binding.setsTV.text = "Подходов: $numberOfApproaches"
        }

        binding.increaseSetsButton.setOnClickListener {
            numberOfApproaches = numberOfApproaches.add(lowChangeValue, lowerBound = 1)
            binding.setsTV.text = "Подходов: $numberOfApproaches"
        }

        binding.decreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.add(-lowChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.increaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.add(lowChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.fastDecreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.add(-fastChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.fastIncreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.add(fastChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.decreaseWeightButton.setOnClickListener {
            weight = weight.add(-lowChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.increaseWeightButton.setOnClickListener {
            weight = weight.add(lowChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.fastDecreaseWeightButton.setOnClickListener {
            weight = weight.add(-fastChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.fastIncreaseWeightButton.setOnClickListener {
            weight = weight.add(fastChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }
    }

    override fun show() {
        setContentView(binding.root)
        super.show()
    }

    interface OnExerciseCreatedListener {
        fun onExerciseCreated(exercise: Exercise)
    }
}