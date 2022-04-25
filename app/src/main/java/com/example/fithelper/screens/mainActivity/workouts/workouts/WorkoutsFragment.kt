package com.example.fithelper.screens.mainActivity.workouts.workouts

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.R
import com.example.fithelper.databinding.FragmentWorkoutBinding
import com.example.fithelper.models.Workout
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.screens.mainActivity.workouts.adapters.workoutAdapter.OnWorkoutItemClickListener
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
            (activity as MainActivity).navController.navigate(R.id.action_workoutsFragment_to_createWorkoutFragment)
        }
    }

    private fun initObservers() {
        vm.workouts.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(vm.workouts.value ?: mutableListOf(), object :
            OnWorkoutItemClickListener {
            override fun getDetails(workout: Workout) {
                val action = WorkoutsFragmentDirections.actionWorkoutsFragmentToChangeWorkoutFragment(workout)
                (activity as MainActivity).navController.navigate(action)
            }

            override fun deleteById(workoutId: String) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Вы действительно хотите удалить данную тренировку?")
                    .setPositiveButton("Да") { _, _ ->
                        vm.deleteWorkout(workoutId)
                    }
                    .setNegativeButton("Нет") { _, _ ->

                    }
                    .create().show()
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