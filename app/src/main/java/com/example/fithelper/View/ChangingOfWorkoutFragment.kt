package com.example.fithelper.View

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.*
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.databinding.FragmentChangingOfWorkoutBinding
import java.text.SimpleDateFormat
import java.util.*


class ChangingOfWorkoutFragment(val workout: Workout) : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentChangingOfWorkoutBinding
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    private lateinit var adapterExercise: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangingOfWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newRecyclerViewList: MutableList<Exercise> = mutableListOf()


        newRecyclerViewList.clear()
        binding.workoutDateTextView.text = "Дата тренировки"
        //val tempList = mutableListOf<Exercise>()
        binding.workoutNameTextView.text = "${workout.name}"
        newRecyclerViewList.addAll(workout.exerciseList!!)

        if(workout.dateInMilliseconds != null && workout.dateInMilliseconds != 0L){
            binding.workoutDateTextView.text =
                "Дата тренировки: ${getDate(workout.dateInMilliseconds!!,"dd.MM.yyyy")}"
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@ChangingOfWorkoutFragment.context)
        adapterExercise = ExerciseAdapter(newRecyclerViewList)
        binding.recyclerView.adapter = adapterExercise

        binding.addExercisesButton.setOnClickListener {
            //BottomSheetAddExerciseFragment().show(parentFragmentManager, "BottomSheetDialog")
            BottomSheetAddExerciseFragment().show(requireFragmentManager(), "BottomSheetDialog")
        }

        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        var dateString: String = "${getDate(workout.dateInMilliseconds!!,"dd.MM.yyyy")}"

        binding.changeWorkoutDateButton.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener
                { _, yearDate, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    val day1 = if (dayOfMonth / 10 == 0) "0${dayOfMonth}" else "${dayOfMonth}"
                    val month1 =
                        if ((monthOfYear + 1) / 10 == 0) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
                    binding.workoutDateTextView.text =
                        ("Дата тренировки: $day1.$month1.$yearDate")
                    dateString = ("$day1.$month1.$yearDate")
                    year = yearDate
                    month = monthOfYear
                    day = dayOfMonth
                }, year, month, day
            )
            dpd.show()
        }

        workoutViewModel.exercise.observe(activity as LifecycleOwner) {
            if (it != null) {
                //newRecyclerViewList.add(it)
                adapterExercise.addExercise(it)
                Toast.makeText(context, "Добавил", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Ошибка при добавлении упражнения", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            val chandegWorkout: Workout
            if (dateString != "") {
                chandegWorkout = Workout("", "",
                    workout.name,
                    convertDateToLong(dateString),
                    newRecyclerViewList
                )
            } else {
                chandegWorkout = Workout("", "",
                    binding.workoutNameTextView.text.toString(),
                    0,
                    newRecyclerViewList
                )
            }
            Toast.makeText(context, "${adapterExercise.itemCount}", Toast.LENGTH_SHORT)
                .show()
            workoutViewModel.changedWorkout.value = chandegWorkout
            //adapterExercise.clear()
            //newRecyclerViewList.clear()
            binding.workoutDateTextView.text =
                ("Дата тренировки: ")
            binding.workoutNameTextView.text = ""
            onDestroy()
            requireFragmentManager().popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        //binding.workoutNameTextView.setText("${workoutViewModel.changedWorkout.value?.name}")
        binding.workoutNameTextView.setText("${workout.name}")
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.parse(date).time
    }

    open fun getDate(dateInMilliseconds: Long, dateFormat: String?): String? {

        val date = Date(dateInMilliseconds)
        val format = SimpleDateFormat(dateFormat)

        return format.format(date)
    }
}



