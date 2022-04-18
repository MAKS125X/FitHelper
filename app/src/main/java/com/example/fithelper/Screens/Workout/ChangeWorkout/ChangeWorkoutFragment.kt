package com.example.fithelper.Screens.Workout.ChangeWorkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.Screens.Exercise.Adapter.ExerciseAdapter
import com.example.fithelper.Extensions.getStringDateFromLong
import com.example.fithelper.Screens.Shared.WorkoutViewModel
import com.example.fithelper.databinding.FragmentChangingOfWorkoutBinding


class ChangeWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentChangingOfWorkoutBinding

    private val workoutForChangeViewModel: WorkoutViewModel by activityViewModels()

    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            binding.workoutNameTextView.text = name
        }

        workoutForChangeViewModel.dateInMilliseconds.observe(activity as LifecycleOwner) { date ->
            binding.workoutDateTextView.text = "Дата тренировки: ${getStringDateFromLong(date, "dd.MM.yyyy") }"
        }
    }

    private fun initRecyclerView() {
        adapter = ExerciseAdapter(workoutForChangeViewModel.exerciseList.value!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

  // fun legacy() {
  //     val newRecyclerViewList: MutableList<Exercise> = mutableListOf()


  //     newRecyclerViewList.clear()
  //     binding.workoutDateTextView.text = "Дата тренировки"
  //     //val tempList = mutableListOf<Exercise>()
  //     binding.workoutNameTextView.text = "${workout.name}"
  //     newRecyclerViewList.addAll(workout.exerciseList!!)

  //     if (workout.dateInMilliseconds != null && workout.dateInMilliseconds != 0L) {
  //         binding.workoutDateTextView.text =
  //             "Дата тренировки: ${
  //                 getStringDateFromLong(
  //                     workout.dateInMilliseconds,
  //                     "dd.MM.yyyy"
  //                 )
  //             }"
  //     }

  //     binding.recyclerView.layoutManager =
  //         LinearLayoutManager(this@ChangeWorkoutFragment.context)
  //     adapter = ExerciseAdapter(newRecyclerViewList)
  //     binding.recyclerView.adapter = adapter


  //     val c = Calendar.getInstance()
  //     var year = c.get(Calendar.YEAR)
  //     var month = c.get(Calendar.MONTH)
  //     var day = c.get(Calendar.DAY_OF_MONTH)
  //     var dateString: String =
  //         "${getStringDateFromLong(workout.dateInMilliseconds, "dd.MM.yyyy")}"


  //     exercisesViewModel.exercises.observe(activity as LifecycleOwner) {
  //         if (it != null) {
  //             //newRecyclerViewList.add(it)
  //             adapter.addExercise(it[0])
  //             Toast.makeText(context, "Добавил", Toast.LENGTH_SHORT)
  //                 .show()
  //         } else {
  //             Toast.makeText(context, "Ошибка при добавлении упражнения", Toast.LENGTH_SHORT)
  //                 .show()
  //         }
  //     }
  // }

}



