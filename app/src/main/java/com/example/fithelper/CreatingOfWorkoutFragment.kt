package com.example.fithelper

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class CreatingOfWorkoutFragment : Fragment() {

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

        val list = mutableListOf<Exercise>()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@CreatingOfWorkoutFragment.context)
        adapter = ExerciseAdapter(list)
        binding.recyclerView.adapter = adapter

        binding.addExercisesButton.setOnClickListener {
            //BottomSheetAddExerciseFragment().show(parentFragmentManager, "BottomSheetDialog")
            BottomSheetAddExerciseFragment().show(requireFragmentManager(), "BottomSheetDialog")
        }

        var c = Calendar.getInstance()
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
                if (dateString != "") {
                    workoutViewModel.workout.value =
                        Workout(
                            binding.workoutNameEditText.text.toString(),
                            convertDateToLong(dateString),
                            list
                        )
                } else {
                    workoutViewModel.workout.value =
                        Workout(binding.workoutNameEditText.text.toString(), 0, list)
                }
                onDestroy()
                requireFragmentManager().popBackStack()
            }
        }
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.parse(date).time
    }

    fun showAlertDialogBuilder(view: View) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Удалить все выделенные упражнения?")
            .setPositiveButton("Вернуться обратно") { dialog, which ->
            }
            .setNegativeButton("Удалить") { dialog, which ->
                binding.recyclerView.adapter
            }
            .show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingOfWorkoutFragment()
    }
}