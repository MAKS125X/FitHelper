package com.example.fithelper.screens.mainActivity.workouts.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.DeleteWorkoutDialog
import com.example.fithelper.DeleteWorkoutDialogListener
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.models.Workout
import com.example.fithelper.screens.mainActivity.workouts.adapters.workoutAdapter.OnWorkoutItemClickListener
import com.example.fithelper.R
import com.example.fithelper.screens.mainActivity.workouts.changeWorkout.ChangeWorkoutViewModel
import com.example.fithelper.screens.shared.WorkoutsViewModel
import com.example.fithelper.screens.mainActivity.workouts.adapters.workoutAdapter.WorkoutAdapter
import com.example.fithelper.databinding.FragmentWorkoutBinding

class WorkoutsFragment : Fragment(), DeleteWorkoutDialogListener {

    lateinit var binding: FragmentWorkoutBinding

    // todo: Переписать логику с workoutForChange на передачу через граф
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
                val action = WorkoutsFragmentDirections.actionWorkoutsFragmentToChangeWorkoutFragment(workout)
                (activity as MainActivity).navController.navigate(action)
            }

            override fun deleteById(workoutId: String) {
                val bundle = Bundle()
                bundle.putString("workoutId", workoutId)


                //setFragmentResult("requestKey", bundleOf("bundleKey" to workoutId ))
                val dialog = DeleteWorkoutDialog()
                dialog.arguments = bundle
                //dialog.setTargetFragment(this@WorkoutsFragment, 0)
                dialog.show(childFragmentManager,"DeleteWorkoutDialog")


            }
        })
        binding.workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutRecyclerView.adapter = adapter
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, workoutId: String) {
        workoutsViewModel.deleteWorkout(workoutId)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutsFragment()
    }
}