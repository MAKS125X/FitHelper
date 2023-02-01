package com.example.fithelper.screens.common.createExerciseDialogFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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
    private lateinit var prefs: SharedPreferences

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

        prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        initClicks()
        initObservers()

        binding.weightSeekBar.max = prefs.getInt(PREF_STAG, 100)
        binding.weightSeekBar.progress = vm.weight.value ?: 0

        binding.weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                vm.setWeight(p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

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

        const val LOWER_BOUND_REPETITIONS = 1
        const val LOWER_BOUND_APPROACHES = 1

        const val PREFS_NAME = "settings"
        const val PREF_STAG = "APP_PREFERENCES_MAX_WEIGHT"

    }
}