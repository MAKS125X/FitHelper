package com.example.fithelper.screens.mainActivity.workouts.workouts

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentWorkoutBinding
import com.example.fithelper.models.Workout
import com.example.fithelper.screens.mainActivity.workouts.adapters.WorkoutAdapter

class WorkoutsFragment : Fragment() {
    lateinit var binding: FragmentWorkoutBinding

    private val vm: WorkoutsViewModel by viewModels()

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
            findNavController().navigate(R.id.action_workoutsFragment_to_createWorkoutFragment)
        }
    }

    private fun initObservers() {
        vm.workouts.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(vm.workouts.value ?: mutableListOf(), object : WorkoutAdapter.OnWorkoutItemClickListener {
            override fun getDetails(workout: Workout) {
                val action =
                    WorkoutsFragmentDirections.actionWorkoutsFragmentToChangeWorkoutFragment(workout)
                findNavController().navigate(action)
            }

            override fun deleteById(workoutId: String) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.confirmation_delete_workout))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        vm.deleteWorkout(workoutId)
                    }
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                    }
                    .create().show()
            }
        })
        binding.workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutRecyclerView.adapter = adapter
    }
}