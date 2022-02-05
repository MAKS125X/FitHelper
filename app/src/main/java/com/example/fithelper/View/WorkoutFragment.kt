package com.example.fithelper.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.*
import com.example.fithelper.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {

    lateinit var binding: FragmentWorkoutBinding

    private val creatingOfWorkoutFragment = CreatingOfWorkoutFragment()

    private lateinit var list: MutableList<Workout>

    private lateinit var adapter: WorkoutAdapter

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()



    }

    private fun init() {

        list = mutableListOf()
        val listExercise: MutableList<Exercise> = mutableListOf()
        (0..2).forEach { i -> listExercise.add(Exercise("$i exercise", 4, 12, 20)) }
        (0..5).forEach { i -> list.add(Workout("$i workout", null, listExercise)) }

        binding.apply {
            workoutRecyclerView.layoutManager = LinearLayoutManager(this@WorkoutFragment.context)
            adapter = WorkoutAdapter(list, object : OnWorkoutItemClickListener {
                override fun onClick(position: Int) {
                    Toast.makeText(
                        context,
                        "Вы нажали на элемент с номером $position",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            workoutRecyclerView.adapter = adapter
            createNewWorkoutFlActButton.setOnClickListener {
                Toast.makeText(
                    context,
                    "Добавить новую тренировку",
                    Toast.LENGTH_SHORT
                ).show()
                val transaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.fragment_holder, creatingOfWorkoutFragment)
                    .addToBackStack("Check")
                    .commit()
            }

            exerciseViewModel.workout.observe(activity as LifecycleOwner) {
                if (it != null) {
                    adapter.addWorkout(it)
                } else {
                    Toast.makeText(context, "Ошибка при добавлении тренировки", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutFragment()
    }
}