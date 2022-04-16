package com.example.fithelper

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithelper.Models.Exercise
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
    // todo: delete
    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemExerciseBinding.bind(itemView)

        fun bind(exercise: Exercise) = with(binding) {
            exerciseNameTV.text = exercise.name.toString()
            exerciseSetsTV.text = "Подходов: ${exercise.numberOfApproaches}"
            exerciseRepsTV.text = "Повторений: ${exercise.numberOfRepetitions}"
            exerciseWeightTV.text = "Вес: ${exercise.weight}"

            //  if(exercise.isComplete == null){
            //      exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            //  }
            //  else{
            //      if(exercise.isComplete == true){
            //          exerciseNameTV.paintFlags =
            //              exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //      }
            //      else{
            //          exerciseNameTV.paintFlags = exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            //      }
            //  }

            setTextStyle(exercise.isComplete == true)
            isCompleteCheckBox.setOnCheckedChangeListener { compoundButton, b ->
                setTextStyle(b)
                exercise.isComplete = b
            }

            //  if (isCompleted) {
            //      exerciseNameTV.paintFlags =
            //          exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //  } else {
            //      exerciseNameTV.paintFlags =
            //          exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            //  }
//
            //  isCompleteCheckBox.setOnClickListener {
            //      if (isCompleteCheckBox.isChecked) {
            //          exerciseNameTV.paintFlags =
            //              exerciseNameTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //          exercise.isComplete = true
            //      } else {
            //          exerciseNameTV.paintFlags =
            //              exerciseNameTV.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            //          exercise.isComplete = false
            //      }
            //  }
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