package com.example.fithelper

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.databinding.RecyclerviewExerciseItemBinding

class ExerciseAdapter(private val exercises: MutableList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>(){

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = RecyclerviewExerciseItemBinding.bind(itemView)

        fun bind(exercise: Exercise) = with(binding){
            exerciseNameTV.text = exercise.name.toString()
            exerciseSetsTV.text = "Подходов: ${exercise.numberOfSets}"
            exerciseRepsTV.text = "Повторений: ${exercise.numberOfReps}"
            exerciseWeightTV.text = "Вес: ${exercise.weight}"

            isCompleteCheckBox.setOnClickListener{
                if (isCompleteCheckBox.isChecked){
                    exerciseNameTV.paintFlags =
                        exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else{
                    exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

        fun isChecked() = binding.isCompleteCheckBox.isChecked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_exercise_item, parent, false)
        return ExerciseAdapter.ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
        notifyDataSetChanged()
    }

    fun deleteExercises(position: Int){
        exercises.removeAt(position)
        notifyDataSetChanged()
    }

}