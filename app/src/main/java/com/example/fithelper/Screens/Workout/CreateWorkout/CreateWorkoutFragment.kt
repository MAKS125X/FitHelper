package com.example.fithelper.Screens.Workout.CreateWorkout

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.Screens.Exercise.CreateExercise.CreateExerciseFragment
import com.example.fithelper.ExerciseAdapter
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.Screens.Shared.ExercisesViewModel
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

open class CreateWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentCreatingOfWorkoutBinding

    private val vm: CreateWorkoutViewModel by viewModels()
    private val exercisesViewModel: ExercisesViewModel by activityViewModels()

    private lateinit var adapter: ExerciseAdapter

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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })

    }

    private fun initClicks() {

        binding.addExercisesButton.setOnClickListener {
            val dialog = CreateExerciseFragment()
            dialog.show(parentFragmentManager, "BottomSheetDialog")
        }

        binding.changeWorkoutDateButton.setOnClickListener {
            vm.changeDate(requireContext())
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            try {
                vm.create(exercisesViewModel.exercises.value)
                exercisesViewModel.setExercises(mutableListOf())
                // todo: what is it
                onDestroy()
                requireFragmentManager().popBackStack()
            } catch (ex: IllegalArgumentException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG)
            }
        }
    }

    private fun initObservers() {
        vm.dateInMilliseconds.observe(activity as LifecycleOwner) { date ->
            val dateString = getDate(date, "dd.MM.yyyy")
            binding.workoutDateTextView.text = "Дата тренировки: $dateString"
        }

        exercisesViewModel.exercises.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter(exercisesViewModel.exercises.value!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun getDate(dateInMilliseconds: Long, dateFormat: String?): String? {
        val date = Date(dateInMilliseconds)
        val format = SimpleDateFormat(dateFormat)

        return format.format(date)
    }


    companion object {
        @JvmStatic
        fun newInstance() = CreateWorkoutFragment()
    }
}