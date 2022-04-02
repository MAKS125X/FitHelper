package com.example.fithelper.Screens.Workout.CreateWorkout

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.BottomSheetAddExerciseFragment
import com.example.fithelper.ExerciseAdapter
import com.example.fithelper.Models.Exercise
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.WorkoutViewModel
import com.example.fithelper.databinding.FragmentCreatingOfWorkoutBinding
import java.text.SimpleDateFormat
import java.util.*

open class CreateWorkoutFragment : Fragment() {

    private lateinit var vm: CreateWorkoutViewModel

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

        vm = ViewModelProvider(this).get(CreateWorkoutViewModel::class.java)

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
            LinearLayoutManager(this@CreateWorkoutFragment.context)
        adapter = ExerciseAdapter(newRecyclerViewList)
        binding.recyclerView.adapter = adapter

        binding.addExercisesButton.setOnClickListener {
            //BottomSheetAddExerciseFragment().show(parentFragmentManager, "BottomSheetDialog")
            BottomSheetAddExerciseFragment().show(requireFragmentManager(), "BottomSheetDialog")
        }

        val calendar = Calendar.getInstance()

        binding.changeWorkoutDateButton.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener
                { _, yearDate, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    val day1 = if (dayOfMonth / 10 == 0) "0${dayOfMonth}" else "${dayOfMonth}"
                    val month1 =
                        if ((monthOfYear + 1) / 10 == 0) "0${monthOfYear + 1}" else "${monthOfYear + 1}"

                    val dateString = "$day1.$month1.$yearDate"
                    val date = convertDateToLong(dateString)
                    vm.setDate(date)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }
        //binding.workoutDateTextView.text = "Дата тренировки: ${vm.dateInMilliseconds.value}"

        vm.dateInMilliseconds.observe(viewLifecycleOwner) { date ->
            val dateString = getDate(date, "dd.MM.yyyy")
            binding.workoutDateTextView.text = "Дата тренировки: $dateString"
        }

        workoutViewModel.exercise.observe(activity as LifecycleOwner) {
            if (it != null) {
                adapter.addExercise(it)
                vm.exercises.value?.add(it)
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
                vm.create()

                adapter.clear()
                newRecyclerViewList.clear()
                binding.workoutDateTextView.text =
                    ("Дата тренировки: ")
                binding.workoutNameEditText.text.clear()
                onDestroy()
                requireFragmentManager().popBackStack()
            }
        }


        binding.workoutNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }

            override fun afterTextChanged(p0: Editable?) {
                vm.setName(p0.toString())
            }
        })

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
        fun newInstance() = CreateWorkoutFragment()
    }
}