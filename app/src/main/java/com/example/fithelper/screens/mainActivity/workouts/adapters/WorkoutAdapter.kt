package com.example.fithelper.screens.mainActivity.workouts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.models.Workout
import com.example.fithelper.R
import com.example.fithelper.databinding.ItemWorkoutBinding
import kotlin.math.min

class WorkoutAdapter(
    private val workouts: MutableList<Workout>,
    private val onWorkoutItemClickListener: OnWorkoutItemClickListener
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workouts[position])
        holder.binding.deleteWorkoutButton.setOnClickListener {
            onWorkoutItemClickListener.deleteById(workouts[position].id!!)
        }
        holder.binding.cardView.setOnClickListener {
            onWorkoutItemClickListener.getDetails(workouts[position])
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemWorkoutBinding.bind(itemView)

        fun bind(workout: Workout) = with(binding) {

            // Отображение названия тренировки
            trainingNameTV.text = workout.name ?: "Тренировка"

            // Отображение даты тренировки
            if (workout.dateInMilliseconds == null)
                linearLayout.removeView(trainingDateTV)
            else
                trainingDateTV.text =
                    getStringDateFromLong(workout.dateInMilliseconds, "dd.MM.yyyy")


            // Отображение упражнений
            val countExercises = workout.exerciseList.count()
            if (countExercises <= 3) {
                dividingLine.isVisible = false
                anotherExercisesCountTV.isVisible = false
            } else {
                dividingLine.isVisible = true
                anotherExercisesCountTV.isVisible = true
            }

            if (workout.exerciseList.count() == 0) {
                exercisesNameTV.text = "Упражнений нет"
            } else {
                // Вывод первых трех
                var exercisesString = ""
                for (i in 0 until min(countExercises, 3))
                    exercisesString += "${workout.exerciseList[i]}\n"
                exercisesNameTV.text = exercisesString.dropLast(1)

                // Информация об оставшихся упражнениях
                val remainingExercisesCount = countExercises - 3
                if (remainingExercisesCount > 0) {
                    anotherExercisesCountTV.text = when (remainingExercisesCount % 10) {
                        1 -> "И ещё $remainingExercisesCount упражнение"
                        in 2..4 -> "И ещё $remainingExercisesCount упражнения"
                        in 5..9 -> "И ещё $remainingExercisesCount упражнений"
                        0 -> "И ещё $remainingExercisesCount упражнений"
                        else -> ""
                    }
                }
            }
        }
    }

    interface OnWorkoutItemClickListener {
        fun getDetails(workout: Workout)
        fun deleteById(workoutId: String)
    }
}