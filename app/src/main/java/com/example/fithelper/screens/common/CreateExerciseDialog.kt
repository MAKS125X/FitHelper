package com.example.fithelper.screens.common

import android.content.Context
import com.example.fithelper.databinding.DialogCreateExerciseBinding
import com.example.fithelper.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateExerciseDialog(context: Context, listener: onExerciseCreatedListener)
    : BottomSheetDialog(context) {
    private val binding: DialogCreateExerciseBinding =
        DialogCreateExerciseBinding.inflate(layoutInflater)

    private var numberOfApproaches = 1
    private var numberOfRepetitions = 1
    private var weight = 0

    constructor(context: Context, listener: (Exercise) -> Unit)
            : this(context, object : onExerciseCreatedListener {
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
        // todo: пересмотреть реализацию
        binding.decreaseSetsButton.setOnClickListener {
            numberOfApproaches = numberOfApproaches.change(-lowChangeValue, lowerBound = 1)
            binding.setsTV.text = "Подходов: $numberOfApproaches"
        }

        binding.increaseSetsButton.setOnClickListener {
            numberOfApproaches = numberOfApproaches.change(lowChangeValue, lowerBound = 1)
            binding.setsTV.text = "Подходов: $numberOfApproaches"
        }

        binding.decreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.change(-lowChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.increaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.change(lowChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.fastDecreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.change(-fastChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.fastIncreaseRepsButton.setOnClickListener {
            numberOfRepetitions = numberOfRepetitions.change(fastChangeValue, lowerBound = 1)
            binding.repsTV.text = "Повторений: $numberOfRepetitions"
        }

        binding.decreaseWeightButton.setOnClickListener {
            weight = weight.change(-lowChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.increaseWeightButton.setOnClickListener {
            weight = weight.change(lowChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.fastDecreaseWeightButton.setOnClickListener {
            weight = weight.change(-fastChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }

        binding.fastIncreaseWeightButton.setOnClickListener {
            weight = weight.change(fastChangeValue, lowerBound = 0)
            binding.weightTV.text = "Вес: $weight"
        }
    }

    override fun show() {
        setContentView(binding.root)
        super.show()
    }

    private fun Int.change(value: Int, lowerBound: Int): Int {
        val result = this + value
        return if (result < lowerBound) lowerBound else result
    }

    // todo: как верно расположить?
    interface onExerciseCreatedListener {
        fun onExerciseCreated(exercise: Exercise)
    }
}