package com.example.fithelper

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.Models.Exercise
import com.example.fithelper.databinding.ItemExerciseBinding

class ExerciseAdapter(private val exercises: MutableList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>(){

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemExerciseBinding.bind(itemView)

        fun bind(exercise: Exercise) = with(binding){
            exerciseNameTV.text = exercise.name.toString()
            exerciseSetsTV.text = "Подходов: ${exercise.numberOfApproaches}"
            exerciseRepsTV.text = "Повторений: ${exercise.numberOfRepetitions}"
            exerciseWeightTV.text = "Вес: ${exercise.weight}"

            if(exercise.isComplete == null){
                exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            else{
                if(exercise.isComplete == true){
                    exerciseNameTV.paintFlags =
                        exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else{
                    exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            isCompleteCheckBox.setOnClickListener{
                if (isCompleteCheckBox.isChecked){
                    exerciseNameTV.paintFlags =
                        exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    exercise.isComplete = true
                }
                else{
                    exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    exercise.isComplete = false
                }
            }
        }

        //fun isChecked() = binding.isCompleteCheckBox.isChecked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseAdapter.ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun clear(){
        exercises.clear()
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