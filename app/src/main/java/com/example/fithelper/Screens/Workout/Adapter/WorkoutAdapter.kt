package com.example.fithelper.Screens.Workout.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.Models.Workout
import com.example.fithelper.R
import com.example.fithelper.databinding.ItemWorkoutBinding
import java.text.SimpleDateFormat
import java.util.*
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
            if (workout.dateInMilliseconds != null && workout.dateInMilliseconds != 0L) {
                val date = Date(workout.dateInMilliseconds!!)
                val format = SimpleDateFormat("dd.MM.yyyy")
                trainingDateTV.text = format.format(date)
            } else {
                linearLayout.removeView(trainingDateTV)
            }

            // Отображение упражнений
            if (workout.exerciseList != null) {
                // Вывод первых трех при наличии
                if (workout.exerciseList!!.count() > 0) {
                    var exercisesString = ""
                    for (i in 0 until min(workout.exerciseList!!.count(), 3))
                        exercisesString += "${workout.exerciseList!![i]}\n"
                    exercisesNameTV.text = exercisesString.dropLastWhile{
                        it == '\n'
                    }
                } else {
                    exercisesNameTV.text = "Упражнений нет"
                }
                // Информация об оставшихся упражнениях
                val remainingExercisesCount = workout.exerciseList!!.count() - 3
                if (remainingExercisesCount > 0) {
                    when (remainingExercisesCount % 10) {
                        1 -> anotherExercisesCountTV.text =
                            "И ещё $remainingExercisesCount упражнение"
                        in 2..4 -> anotherExercisesCountTV.text =
                            "И ещё $remainingExercisesCount упражнения"
                        in 5..9 -> anotherExercisesCountTV.text =
                            "И ещё $remainingExercisesCount упражнений"
                        0 -> anotherExercisesCountTV.text =
                            "И ещё $remainingExercisesCount упражнений"
                    }
                } else {
                    linearLayout.removeView(dividingLine)
                    linearLayout.removeView(anotherExercisesCountTV)
                }
            }
        }
    }
}