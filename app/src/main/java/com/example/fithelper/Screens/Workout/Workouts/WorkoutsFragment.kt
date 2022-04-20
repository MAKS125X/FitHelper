package com.example.fithelper.Screens.Workout.Workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.Features.MainActivity
import com.example.fithelper.Models.Workout
import com.example.fithelper.Screens.Workout.Adapter.OnWorkoutItemClickListener
import com.example.fithelper.R
import com.example.fithelper.Screens.Shared.WorkoutViewModel
import com.example.fithelper.Screens.Shared.WorkoutsViewModel
import com.example.fithelper.Screens.Workout.ChangeWorkout.ChangeWorkoutFragment
import com.example.fithelper.Screens.Workout.CreateWorkout.CreateWorkoutFragment
import com.example.fithelper.Screens.Workout.Adapter.WorkoutAdapter
import com.example.fithelper.databinding.FragmentWorkoutBinding

class WorkoutsFragment : Fragment() {

    lateinit var binding: FragmentWorkoutBinding

    // todo: Переписать логику с workoutForChange на передачу через граф
    private val workoutForChangeViewModel: WorkoutViewModel by activityViewModels()
    private val workoutsViewModel: WorkoutsViewModel by activityViewModels()

    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
        initClicks()
    }

    private fun initClicks() = with(binding) {
        createNewWorkoutFlActButton.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_workoutsFragment_to_createWorkoutFragment)
        }
    }

    private fun initObservers() {
        workoutsViewModel.workouts.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(workoutsViewModel.workouts.value ?: mutableListOf(), object :
            OnWorkoutItemClickListener {
            override fun getDetails(workout: Workout) {
                workoutForChangeViewModel.setWorkout(workout)
                (activity as MainActivity).navController.navigate(R.id.action_workoutsFragment_to_changeWorkoutFragment)
            }

            override fun deleteById(workoutId: String) {
                // todo: dialog "rly delete?"
                workoutsViewModel.deleteWorkout(workoutId)
            }
        })
        binding.workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutRecyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutsFragment()
    }
}