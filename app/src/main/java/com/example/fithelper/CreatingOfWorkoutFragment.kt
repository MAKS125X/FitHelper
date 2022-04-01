package com.example.fithelper

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

open class CreatingOfWorkoutFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentCreatingOfWorkoutBinding
    private val workoutViewModel: WorkoutViewModel by activityViewModels()
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatingOfWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newRecyclerViewList: MutableList<Exercise> = mutableListOf<Exercise>()

//        workoutViewModel.changedWorkout.observe(activity as LifecycleOwner) {
//            newRecyclerViewList.clear()
//            binding.workoutNameEditText.text.clear()
//            binding.workoutDateTextView.setText("Дата тренировки")
//            val tempList = it.exerciseList
//            if (tempList != null) {
//                newRecyclerViewList.addAll(tempList)
//            }
//            //if(it.name != null){
//            binding.workoutNameEditText.setText("${it.name}")
//            binding.workoutNameEditText.setText("hhfgbdvsdDx")
//            //}
//            if(it.dateInMilliseconds != null && it.dateInMilliseconds != 0L){
//                binding.workoutDateTextView.setText("Дата тренировки: ${getDate(it.dateInMilliseconds!!,"dd.MM.yyyy")}")
//            }
//        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@CreatingOfWorkoutFragment.context)
        adapter = ExerciseAdapter(newRecyclerViewList)
        binding.recyclerView.adapter = adapter

        binding.addExercisesButton.setOnClickListener {
            //BottomSheetAddExerciseFragment().show(parentFragmentManager, "BottomSheetDialog")
            BottomSheetAddExerciseFragment().show(requireFragmentManager(), "BottomSheetDialog")
        }

        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        var dateString: String = ""

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
                adapter.addExercise(it)
                //binding.recyclerView.adapter
                //newRecyclerViewList.add(it)
            } else {
                Toast.makeText(context, "Ошибка при добавлении упражнения", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.confirmWorkoutCreationButton.setOnClickListener {
            if (adapter.itemCount == 0) {
                Toast.makeText(context, "Добавьте упражнения в тренировку!", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.workoutNameEditText.text.toString().isEmpty()) {
                Toast.makeText(context, "Добавьте название тренировке!", Toast.LENGTH_SHORT).show()
            } else {
                val workout: Workout
                if (dateString != "") {
                    workout = Workout(
                        binding.workoutNameEditText.text.toString(),
                        convertDateToLong(dateString),
                        newRecyclerViewList
                    )
                } else {
                    workout = Workout(
                        binding.workoutNameEditText.text.toString(),
                        0,
                        newRecyclerViewList
                    )
                }

                workoutViewModel.createdWorkout.value = workout

                adapter.clear()
                newRecyclerViewList.clear()
                binding.workoutDateTextView.text =
                    ("Дата тренировки: ")
                binding.workoutNameEditText.text.clear()
                onDestroy()
                requireFragmentManager().popBackStack()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //binding.workoutNameEditText.setText("${workoutViewModel.changedWorkout.value?.name}")
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

//    fun showAlertDialogBuilder(view: View) {
//        MaterialAlertDialogBuilder(requireContext()).setTitle("Удалить все выделенные упражнения?")
//            .setPositiveButton("Вернуться обратно") { dialog, which ->
//            }
//            .setNegativeButton("Удалить") { dialog, which ->
//                binding.recyclerView.adapter
//            }
//            .show()
//    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingOfWorkoutFragment()
    }
}