package com.example.fithelper.screens.common.createExerciseDialogFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fithelper.R
import com.example.fithelper.databinding.DialogCreateExerciseBinding
import com.example.fithelper.extensions.add
import com.example.fithelper.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

class CreateExerciseDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogCreateExerciseBinding

    private val args by navArgs<CreateExerciseDialogFragmentArgs>()
    private val vm: CreateExerciseViewModel by viewModels { CreateExerciseFactory(args.listener) }

    private fun initClicks() = with(binding) {
        completeAddExerciseButton.setOnClickListener {
            vm.completeCreateExercise()
            findNavController().navigateUp()
        }

        decreaseSetsButton.setOnClickListener {
            val numberOfApproaches = vm.numberOfApproaches.value?.add(
                -SLOW_CHANGE_VALUE,
                LOWER_BOUND_APPROACHES
            )
            vm.setNumberOfApproaches(numberOfApproaches)
        }
        increaseSetsButton.setOnClickListener {
            val numberOfApproaches = vm.numberOfApproaches.value?.add(
                SLOW_CHANGE_VALUE,
                LOWER_BOUND_APPROACHES
            )
            vm.setNumberOfApproaches(numberOfApproaches)
        }

        decreaseRepsButton.setOnClickListener {
            val numberOfRepetitions = vm.numberOfRepetitions.value?.add(
                -SLOW_CHANGE_VALUE,
                LOWER_BOUND_REPETITIONS
            )
            vm.setNumberOfRepetitions(numberOfRepetitions)
        }
        increaseRepsButton.setOnClickListener {
            val numberOfRepetitions = vm.numberOfRepetitions.value?.add(
                SLOW_CHANGE_VALUE,
                LOWER_BOUND_REPETITIONS
            )
            vm.setNumberOfRepetitions(numberOfRepetitions)
        }
        fastDecreaseRepsButton.setOnClickListener {
            val numberOfRepetitions = vm.numberOfRepetitions.value?.add(
                -FAST_CHANGE_VALUE,
                LOWER_BOUND_REPETITIONS
            )
            vm.setNumberOfRepetitions(numberOfRepetitions)
        }
        fastIncreaseRepsButton.setOnClickListener {
            val numberOfRepetitions = vm.numberOfRepetitions.value?.add(
                FAST_CHANGE_VALUE,
                LOWER_BOUND_REPETITIONS
            )
            vm.setNumberOfRepetitions(numberOfRepetitions)
        }

        decreaseWeightButton.setOnClickListener {
            val weight = vm.weight.value?.add(
                -SLOW_CHANGE_VALUE,
                LOWER_BOUND_WEIGHT
            )
            vm.setWeight(weight)
        }
        increaseWeightButton.setOnClickListener {
            val weight = vm.weight.value?.add(
                SLOW_CHANGE_VALUE,
                LOWER_BOUND_WEIGHT
            )
            vm.setWeight(weight)
        }
        fastDecreaseWeightButton.setOnClickListener {
            val weight = vm.weight.value?.add(
                -FAST_CHANGE_VALUE,
                LOWER_BOUND_WEIGHT
            )
            vm.setWeight(weight)
        }
        fastIncreaseWeightButton.setOnClickListener {
            val weight = vm.weight.value?.add(
                FAST_CHANGE_VALUE,
                LOWER_BOUND_WEIGHT
            )
            vm.setWeight(weight)
        }
    }

    private fun initObservers() = with(binding) {
        vm.numberOfApproaches.observe(viewLifecycleOwner) { numberOfApproaches ->
            setsTV.text = root.resources.getString(
                R.string.placeholder_count_approaches,
                numberOfApproaches
            )
        }

        vm.numberOfRepetitions.observe(viewLifecycleOwner) { numberOfRepetitions ->
            repsTV.text = root.resources.getString(
                R.string.placeholder_count_repeats,
                numberOfRepetitions
            )
        }

        vm.weight.observe(viewLifecycleOwner) { weight ->
            weightTV.text = root.resources.getString(
                R.string.placeholder_weigh,
                weight
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateExerciseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        initObservers()

        binding.exerciseNameET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })
    }

    interface OnExerciseCreatedListener : Serializable {
        fun onExerciseCreated(exercise: Exercise)
    }

    companion object {
        const val FAST_CHANGE_VALUE = 5
        const val SLOW_CHANGE_VALUE = 1

        const val LOWER_BOUND_WEIGHT = 0
        const val LOWER_BOUND_REPETITIONS = 1
        const val LOWER_BOUND_APPROACHES = 1
    }
}