package com.example.fithelper.screens.mainActivity.workouts.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.R
import com.example.fithelper.databinding.ItemExerciseBinding
import com.example.fithelper.models.Exercise

class ExerciseAdapter(
    private val exercises: MutableList<Exercise>,
    private val isChangeable: Boolean = true
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position], isChangeable)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemExerciseBinding.bind(itemView)

        fun bind(exercise: Exercise, isChangeable: Boolean) = with(binding) {
            exerciseNameTV.text =
                exercise.name ?: root.resources.getString(R.string.exercise_name)
            exerciseSetsTV.text = root.resources.getString(
                R.string.placeholder_count_approaches,
                exercise.numberOfApproaches
            )
            exerciseRepsTV.text = root.resources.getString(
                R.string.placeholder_count_repeats,
                exercise.numberOfRepetitions
            )
            exerciseWeightTV.text = root.resources.getString(
                R.string.placeholder_weigh,
                exercise.weight
            )

            if (!isChangeable) {
                isCompleteCheckBox.isVisible = false
                return@with
            }

            isCompleteCheckBox.isChecked = exercise.isComplete
            setTextStyle(exercise.isComplete)

            isCompleteCheckBox.setOnCheckedChangeListener { _, bool ->
                setTextStyle(bool)
                exercise.isComplete = bool
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