package com.example.fithelper.Screens.Workout.Workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val workoutForChangeViewModel: WorkoutViewModel by activityViewModels()
    private val workoutsViewModel: WorkoutsViewModel by activityViewModels()

    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_holder, CreateWorkoutFragment())
                .addToBackStack("Check")
                .commit()
        }
    }

    private fun initObservers() {
        workoutsViewModel.workouts.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(workoutsViewModel.workouts.value!!, object :
            OnWorkoutItemClickListener {
            override fun getDetails(workout: Workout) {
                workoutForChangeViewModel.setWorkout(workout)

                val transaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.fragment_holder, ChangeWorkoutFragment())
                    .addToBackStack("Check")
                    .commit()
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