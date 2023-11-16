package com.example.fithelper.screens.mainActivity.workouts.changeWorkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentChangingOfWorkoutBinding
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.screens.mainActivity.workouts.adapters.ExerciseAdapter


class ChangeWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentChangingOfWorkoutBinding
    private lateinit var adapter: ExerciseAdapter

    private val args by navArgs<ChangeWorkoutFragmentArgs>()
    private val workoutForChangeViewModel: ChangeWorkoutViewModel by viewModels {
        ChangeWorkoutFactory(
            args.workoutForChange
        )
    }

    private fun initObservers() {
        workoutForChangeViewModel.name.observe(viewLifecycleOwner) { name ->
            binding.workoutNameTextView.text =
                name ?: binding.root.resources.getString(R.string.workout_name)
        }

        workoutForChangeViewModel.dateInMilliseconds.observe(viewLifecycleOwner) { date ->
            if (date == null || date == 0L) {
                binding.workoutDateTextView.isVisible = false
            } else {
                binding.workoutDateTextView.isVisible = true
                binding.workoutDateTextView.text = getString(
                    R.string.placeholder_workout_date,
                    getStringDateFromLong(date, "dd.MM.yyyy")
                )
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter()
        adapter.submitList(workoutForChangeViewModel.exerciseList.value)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_changeWorkoutFragment_to_workoutsFragment)
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
}



