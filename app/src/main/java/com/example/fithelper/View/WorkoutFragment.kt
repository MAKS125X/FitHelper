package com.example.fithelper.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithelper.*
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.databinding.FragmentWorkoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class WorkoutFragment : Fragment() {

    lateinit var binding: FragmentWorkoutBinding

    private val creatingOfWorkoutFragment = CreatingOfWorkoutFragment()

    private lateinit var changingOfWorkout: ChangingOfWorkout

    private var listWorkout: MutableList<Workout> = mutableListOf()

    private lateinit var adapter: WorkoutAdapter

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        //val firebaseUserId = loginViewModel.userId
        val firestoreDataBase =
            FirebaseFirestore.getInstance()
                .collection("Users")
                .document(Firebase.auth.currentUser!!.uid)
                .collection("Workouts")

//        firestoreDataBase.get().addOnSuccessListener { result ->
//                adapter.clear()
//                for (workoutItem in result) {
//                    val workout = workoutItem.toObject(Workout::class.java)
//                    Log.i("TAG", "${workout.name}")
//                    adapter.addWorkout(workout)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.i("TAG", "Давай по новой: ", exception)
//            }

        binding.apply {
            workoutRecyclerView.layoutManager = LinearLayoutManager(this@WorkoutFragment.context)
            adapter = WorkoutAdapter(listWorkout, object : OnWorkoutItemClickListener {
                override fun onClick(position: Int) {
//                    Toast.makeText(
//                        context,
//                        "Вы нажали на элемент с номером $position, ${listWorkout[position].name}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    val transaction = requireFragmentManager().beginTransaction()
                    //workoutViewModel.changedWorkout.value = listWorkout[position]
                    changingOfWorkout = ChangingOfWorkout(listWorkout[position])
                    transaction.replace(R.id.fragment_holder, changingOfWorkout)
                        .addToBackStack("Check")
                        .commit()
                }
            })

        workoutRecyclerView.adapter = adapter
        createNewWorkoutFlActButton.setOnClickListener {
//                Toast.makeText(
//                    context,
//                    "Добавить новую тренировку",
//                    Toast.LENGTH_SHORT
//                ).show()
            val transaction = requireFragmentManager().beginTransaction()
            //workoutViewModel.changedWorkout.value = Workout()
            transaction.replace(R.id.fragment_holder, creatingOfWorkoutFragment)
                .addToBackStack("Check")
                .commit()
        }

            firestoreDataBase.addSnapshotListener{value, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    Log.e("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.w("TAG", "Listen NOT failed.", e)
                Log.e("TAG", "Listen NOT failed.", e)

                Toast.makeText(context, "Ошибки нет", Toast.LENGTH_SHORT).show()

                //listWorkout = mutableListOf()
                adapter.clear()
                for (doc in value!!){
                    val workout : Workout= doc.toObject(Workout::class.java)
                    adapter.addWorkout(workout)
                    //listWorkout.add(workout)
                }
            }

            workoutViewModel.changedWorkout.observe(activity as LifecycleOwner){
                if (it != null){
                    val firestoreDataBase = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .document(Firebase.auth.currentUser!!.uid)
                        .collection("Workouts")
                        .document(it.toString())
                    firestoreDataBase.set(it)
                }
            }

            workoutViewModel.createdWorkout.observe(activity as LifecycleOwner) {
                if (it != null) {
                    //adapter.addWorkout(it)
                    val firestoreDataBase = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .document(Firebase.auth.currentUser!!.uid)
                        .collection("Workouts")
                        .document(it.toString())
//                    val checker = firestoreDataBase.get().getResult()?.exists()
//
//                    if(checker != null){
//                        if(checker){
//                            firestoreDataBase.update(it)
//                        }
//                    }
                    firestoreDataBase.set(it)
                } else {
                    Toast.makeText(context, "Ошибка при добавлении тренировки", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

//        firestoreDataBase.addSnapshotListener{value, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                Log.e(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            Log.w(TAG, "Listen NOT failed.", e)
//            Log.e(TAG, "Listen NOT failed.", e)
//            //listWorkout = mutableListOf()
//            for (doc in value!!){
//                val workout : Workout= doc.toObject<Workout>(Workout::class.java)
//                adapter.addWorkout(workout)
//            }
//        }

//        val listExercise: MutableList<Exercise> = mutableListOf()
//        (0..2).forEach { i -> listExercise.add(Exercise("$i exercise", 4, 12, 20)) }
//        (0..5).forEach { i -> listWorkout.add(Workout("$i workout", null, listExercise)) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkoutFragment()
    }
}