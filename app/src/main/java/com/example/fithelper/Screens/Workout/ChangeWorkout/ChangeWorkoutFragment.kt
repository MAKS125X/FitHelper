package com.example.fithelper.Screens.Workout.ChangeWorkout

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.Screens.Exercise.Adapter.ExerciseAdapter
import com.example.fithelper.Extensions.getStringDateFromLong
import com.example.fithelper.Features.MainActivity
import com.example.fithelper.R
import com.example.fithelper.Screens.Shared.WorkoutViewModel
import com.example.fithelper.databinding.FragmentChangingOfWorkoutBinding


class ChangeWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentChangingOfWorkoutBinding

    // todo: передавать workout через граф, обновлять по Id, избавиться ViewModel
    private val workoutForChangeViewModel: WorkoutViewModel by activityViewModels()

    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            (activity as MainActivity).navController.navigate(R.id.action_changeWorkoutFragment_to_workoutsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangingOfWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        workoutForChangeViewModel.updateWorkout()
        super.onDestroyView()
    }

    private fun initObservers() {
        workoutForChangeViewModel.name.observe(activity as LifecycleOwner) { name ->
            binding.workoutNameTextView.text = name ?: "Тренировка"
        }

        workoutForChangeViewModel.dateInMilliseconds.observe(activity as LifecycleOwner) { date ->
            if (date == null || date == 0L) {
                binding.workoutDateTextView.isVisible = false
            } else {
                binding.workoutDateTextView.isVisible = true
                val dateString = getStringDateFromLong(date, "dd.MM.yyyy")
                binding.workoutDateTextView.text = "Дата тренировки: $dateString}"
            }

        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter(workoutForChangeViewModel.exerciseList.value ?: mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }
}



