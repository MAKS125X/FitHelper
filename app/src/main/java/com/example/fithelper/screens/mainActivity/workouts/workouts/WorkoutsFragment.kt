package com.example.fithelper.screens.mainActivity.workouts.workouts

import android.content.DialogInterface
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
import com.example.fithelper.screens.common.confirmationDialogFragment.ConfirmationDialogFragment
import com.example.fithelper.screens.mainActivity.workouts.adapters.WorkoutAdapter

class WorkoutsFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private lateinit var adapter: WorkoutAdapter

    private val vm: WorkoutsViewModel by viewModels()

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

    private fun initRecyclerView() = with(binding) {
        adapter = WorkoutAdapter(
            vm.workouts.value ?: mutableListOf(),
            object : WorkoutAdapter.OnWorkoutItemClickListener {
                override fun getDetails(workout: Workout) {
                    val action =
                        WorkoutsFragmentDirections.actionWorkoutsFragmentToChangeWorkoutFragment(
                            workout
                        )
                    findNavController().navigate(action)
                }

                override fun deleteById(workoutId: String) {
                    val action =
                        WorkoutsFragmentDirections.actionWorkoutsFragmentToConfirmationDialogFragment(
                            getString(R.string.confirmation_delete_workout)
                        ).setListener(object : ConfirmationDialogFragment.OnConfirmationListener {
                            override fun confirmButtonClicked(
                                dialogInterface: DialogInterface,
                                buttonClicked: Int
                            ) {
                                vm.deleteWorkout(workoutId)
                            }

                            override fun cancelButtonClicked(
                                dialogInterface: DialogInterface,
                                buttonClicked: Int
                            ) {
                            }
                        })
                    findNavController().navigate(action)
                }
            })
        workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        workoutRecyclerView.adapter = adapter
    }

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
}