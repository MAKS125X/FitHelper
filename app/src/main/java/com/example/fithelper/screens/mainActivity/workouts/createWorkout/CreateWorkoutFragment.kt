package com.example.fithelper.screens.mainActivity.workouts.createWorkout

import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.screens.common.CreateExerciseDialog
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.R
import com.example.fithelper.screens.mainActivity.workouts.adapters.exerciseAdapter.ExerciseAdapter
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import java.util.*

open class CreateWorkoutFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentCreatingOfWorkoutBinding

    private val vm: CreateWorkoutViewModel by viewModels()

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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })

    }

    private fun initClicks() {
        binding.addExercisesButton.setOnClickListener {
            CreateExerciseDialog(requireContext()) { exercise ->
                vm.addExercise(exercise)
            }.show()
        }

        binding.changeWorkoutDateButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            vm.create()
            (activity as MainActivity).navController.navigate(R.id.action_createWorkoutFragment_to_workoutsFragment)
        }
    }

    private fun initObservers() {
        vm.dateInMilliseconds.observe(viewLifecycleOwner) { date ->
            if (date == null) {
                binding.workoutDateTextView.text = "Укажите дату тренировки"
                return@observe
            }

            binding.workoutDateTextView.text =
                "Дата тренировки: ${getStringDateFromLong(date, "dd.MM.yyyy")}"
        }

        vm.exercises.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter(vm.exercises.value ?: mutableListOf())
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