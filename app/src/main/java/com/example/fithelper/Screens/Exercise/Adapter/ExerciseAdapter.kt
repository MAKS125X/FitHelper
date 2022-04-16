package com.example.fithelper.Screens.Exercise.Adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.Models.Exercise
import com.example.fithelper.R
import com.example.fithelper.databinding.ItemExerciseBinding

class ExerciseAdapter(private val exercises: MutableList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemExerciseBinding.bind(itemView)

        fun bind(exercise: Exercise) = with(binding) {
            exerciseNameTV.text = exercise.name.toString()
            exerciseSetsTV.text = "Подходов: ${exercise.numberOfApproaches}"
            exerciseRepsTV.text = "Повторений: ${exercise.numberOfRepetitions}"
            exerciseWeightTV.text = "Вес: ${exercise.weight}"
            isCompleteCheckBox.isChecked = exercise.isComplete ?: false

            setTextStyle(exercise.isComplete == true)
            isCompleteCheckBox.setOnCheckedChangeListener { compoundButton, b ->
                setTextStyle(b)
                exercise.isComplete = b
            }
        }

        private fun ItemExerciseBinding.setTextStyle(b: Boolean) {
            if (b)
                exerciseNameTV.paintFlags =
                    exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                exerciseNameTV.paintFlags =
                    exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}