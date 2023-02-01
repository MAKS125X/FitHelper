package com.example.fithelper.screens.mainActivity.workouts.createWorkout

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.models.Exercise
import com.example.fithelper.screens.common.createExerciseDialogFragment.CreateExerciseDialogFragment
import com.example.fithelper.screens.common.datePickerDialogFragment.DatePickerDialogFragment
import com.example.fithelper.screens.mainActivity.workouts.adapters.ExerciseAdapter
import java.util.*

open class CreateWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentCreatingOfWorkoutBinding
    private lateinit var adapter: ExerciseAdapter

    private val vm: CreateWorkoutViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }

    private fun initClicks() = with(binding) {
        addExercisesButton.setOnClickListener {
            val action =
                CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToCreateExerciseDialogFragment(
                    object :
                        CreateExerciseDialogFragment.OnExerciseCreatedListener {
                        override fun onExerciseCreated(exercise: Exercise) {
                            vm.addExercise(exercise)
                        }
                    })
            findNavController().navigate(action)
        }

        changeWorkoutDateButton.setOnClickListener {
            val action =
                CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToDatePickerDialogFragment(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).setListener(object : DatePickerDialogFragment.OnDateSetListener {
                    override fun onDateSet(
                        datePicker: DatePicker?,
                        year: Int,
                        monthOfYear: Int,
                        dayOfMonth: Int
                    ) {
                        calendar.set(year, monthOfYear, dayOfMonth)
                        vm.setDate(calendar.timeInMillis)
                    }

                })
            findNavController().navigate(action)
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            vm.create()
            findNavController().navigateUp()
        }
    }

    private fun initObservers() = with(binding) {
        vm.dateInMilliseconds.observe(viewLifecycleOwner) { date ->
            val text =
                if (date == null)
                    resources.getString(R.string.specify_workout_date)
                else
                    resources.getString(
                        R.string.placeholder_workout_date,
                        getStringDateFromLong(date, "dd.MM.yyyy")
                    )

            workoutDateTextView.text = text
        }

        vm.exercises.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() = with(binding) {
        adapter = ExerciseAdapter(vm.exercises.value ?: mutableListOf(), isChangeable = false)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_createWorkoutFragment_to_workoutsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatingOfWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
        initClicks()

        binding.workoutNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })
    }
}