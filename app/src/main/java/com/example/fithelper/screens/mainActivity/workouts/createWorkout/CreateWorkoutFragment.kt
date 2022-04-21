package com.example.fithelper.screens.mainActivity.workouts.createWorkout

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.R
import com.example.fithelper.screens.mainActivity.workouts.adapters.exerciseAdapter.ExerciseAdapter
import com.example.fithelper.screens.shared.ExercisesViewModel
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import java.util.*

open class CreateWorkoutFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentCreatingOfWorkoutBinding

    private val vm: CreateWorkoutViewModel by viewModels { CreateWorkoutFactory(requireContext(), this) }
    private val exercisesViewModel: ExercisesViewModel by activityViewModels()

    private lateinit var adapter: ExerciseAdapter

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            (activity as MainActivity).navController.navigate(R.id.action_createWorkoutFragment_to_workoutsFragment)
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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })

    }

    private fun initClicks() {
        binding.addExercisesButton.setOnClickListener {
            // todo: Передавать exercise как результат, отказаться от exercisesViewModel
            (activity as MainActivity).navController.navigate(R.id.action_createWorkoutFragment_to_createExerciseFragment)
        }

        binding.changeWorkoutDateButton.setOnClickListener {
            vm.changeDate()
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            try {
                vm.create(exercisesViewModel.exercises.value)
                exercisesViewModel.setExercises(mutableListOf())
                (activity as MainActivity).navController.navigate(R.id.action_createWorkoutFragment_to_workoutsFragment)
            } catch (ex: IllegalArgumentException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObservers() {
        vm.dateInMilliseconds.observe(activity as LifecycleOwner) { date ->
            if (date == null) {
                binding.workoutDateTextView.text = "Укажите дату тренировки"
                return@observe
            }

            val dateString = getStringDateFromLong(date, "dd.MM.yyyy")
            binding.workoutDateTextView.text = "Дата тренировки: $dateString"
        }

        exercisesViewModel.exercises.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter(exercisesViewModel.exercises.value ?: mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = CreateWorkoutFragment()
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(year, monthOfYear, dayOfMonth)
        vm.setDate(calendar.timeInMillis)
    }
}