package com.example.fithelper.screens.mainActivity.workouts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.R
import com.example.fithelper.databinding.ItemWorkoutBinding
import com.example.fithelper.extensions.getStringDateFromLong
import com.example.fithelper.models.Workout
import kotlin.math.min

class WorkoutAdapter(
    private val onWorkoutItemClickListener: OnWorkoutItemClickListener
) : ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.binding.deleteWorkoutButton.setOnClickListener {
            onWorkoutItemClickListener.deleteById(currentList[position].id)
        }
        holder.binding.cardView.setOnClickListener {
            onWorkoutItemClickListener.getDetails(
                currentList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemWorkoutBinding.bind(itemView)

        fun bind(workout: Workout) = with(binding) {

            // Отображение названия тренировки
            trainingNameTV.text =
                workout.name ?: root.resources.getString(R.string.workout_name)

            // Отображение даты тренировки
            if (workout.dateInMilliseconds == null)
                linearLayout.removeView(trainingDateTV)
            else
                trainingDateTV.text = root.resources.getString(
                    R.string.placeholder_workout_date,
                    getStringDateFromLong(workout.dateInMilliseconds, "dd.MM.yyyy")
                )

            // Отображение упражнений
            val countExercises = workout.exerciseList.count()
            if (countExercises == 0) {
                dividingLine.isVisible = false
                anotherExercisesCountTV.isVisible = false
            } else {
                dividingLine.isVisible = true
                anotherExercisesCountTV.isVisible = true
            }

            if (countExercises == 0) {
                exercisesNameTV.text = root.resources.getString(R.string.no_exercises)
            } else {
                // Вывод первых трех
                var exercisesString = ""
                for (i in 0 until min(countExercises, 3))
                    exercisesString += "${
                        workout.exerciseList[i].name ?: root.resources.getString(
                            R.string.exercise_name
                        )
                    }\n"
                exercisesNameTV.text = exercisesString.dropLast(1)

                // Информация об оставшихся упражнениях
                anotherExercisesCountTV.text = root.resources.getString(
                    R.string.placeholder_exercise_total_count,
                    countExercises
                )
            }
        }
    }

    interface OnWorkoutItemClickListener {
        fun getDetails(workout: Workout)
        fun deleteById(workoutId: String)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(
                oldItem: Workout,
                newItem: Workout
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Workout,
                newItem: Workout
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}