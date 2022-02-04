package com.example.fithelper

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

class CreatingOfWorkoutFragment : Fragment() {

    private lateinit var binding: FragmentCreatingOfWorkoutBinding
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
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

        exerciseViewModel.exercise.observe(activity as LifecycleOwner) {
            if (it != null) {
                adapter.addExercise(it)
            } else {
                Toast.makeText(context, "Ошибка при добавлении упражнения", Toast.LENGTH_SHORT)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingOfWorkoutFragment()
    }
}