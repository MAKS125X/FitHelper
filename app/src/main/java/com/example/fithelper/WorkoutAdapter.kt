package com.example.fithelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.Models.Workout
import com.example.fithelper.databinding.ItemWorkoutBinding
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter(private val workouts: MutableList<Workout>, private val onWorkoutItemClickListener: OnWorkoutItemClickListener) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

//    interface OnWorkoutItemClickListener{
//        fun onItemClick(position: Int)
//    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemWorkoutBinding.bind(itemView)

        fun bind(workout: Workout) = with(binding) {

            // Отображение названия тренировки
            trainingNameTV.text = workout.name

            // Отображение даты тренировки
            if(workout.dateInMilliseconds != null && workout.dateInMilliseconds != 0L){
                val date = Date(workout.dateInMilliseconds!!)
                val format = SimpleDateFormat("dd.MM.yyyy")
                trainingDateTV.text = format.format(date)
            }
            else{
                //trainingDateTV.visibility = View.INVISIBLE
                linearLayout.removeView(trainingDateTV)
            }

            // Отображение первых упражнений и количество прочих при их наличии
            if(workout.exerciseList!!.count() > 3){
                exercisesNameTV.text = "${workout.exerciseList!![0]}\n${workout.exerciseList!![1]}\n" +
                        "${workout.exerciseList!![2]}"

                val remainingExercisesCount = workout.exerciseList!!.count() - 3
                var i = remainingExercisesCount
                var iterator = i
                while(iterator != 0){
                    i %= 10
                    iterator /= 10
                }
                when(i){
                    1 -> anotherExercisesCountTV.text = "И ещё $remainingExercisesCount упражнение"
                    in 2..4 -> anotherExercisesCountTV.text = "И ещё $remainingExercisesCount упражнения"
                    in 5..9 -> anotherExercisesCountTV.text = "И ещё $remainingExercisesCount упражнений"
                    0 -> anotherExercisesCountTV.text = "И ещё $remainingExercisesCount упражнений"
                }
            }
            else{
                var str: String = ""
                for(exercise in workout.exerciseList!!){
                    str += "$exercise\n"
                }
                exercisesNameTV.text = str.trim()
                linearLayout.removeView(dividingLine)
                linearLayout.removeView(anotherExercisesCountTV)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workouts[position])
        holder.binding.cardView.setOnClickListener {
            onWorkoutItemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    fun clear(){
        workouts.clear()
    }

    fun addWorkout(workout: Workout) {
        workouts.add(workout)
        notifyDataSetChanged()
    }

}