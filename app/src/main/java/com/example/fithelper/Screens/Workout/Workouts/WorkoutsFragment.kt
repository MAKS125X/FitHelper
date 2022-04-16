package com.example.fithelper.Screens.Workout.Workouts

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
import com.example.fithelper.Models.Workout
import com.example.fithelper.Repository.LoginViewModel
import com.example.fithelper.Screens.Shared.ExercisesViewModel
import com.example.fithelper.Screens.Workout.CreateWorkout.CreateWorkoutFragment
import com.example.fithelper.Screens.Workout.CreateWorkout.CreateWorkoutViewModel
import com.example.fithelper.View.ChangingOfWorkoutFragment
import com.example.fithelper.databinding.FragmentWorkoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class WorkoutsFragment : Fragment() {

    lateinit var binding: FragmentWorkoutBinding

    private val vm: WorkoutsViewModel by activityViewModels()

    private lateinit var adapter: WorkoutAdapter


    // private val creatingOfWorkoutFragment = CreateWorkoutFragment()
    // private lateinit var changingOfWorkout: ChangingOfWorkoutFragment
    // private val exerciseViewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
        initClicks()

    }

    private fun initClicks() = with(binding) {
        createNewWorkoutFlActButton.setOnClickListener {
            vm.initializeWorkouts()
        }
    }

    private fun initObservers() {
        vm.workouts.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(vm.workouts.value!!, object : OnWorkoutItemClickListener {
            override fun onClick(position: Int) {
                // todo: ask maxim
                // val transaction = requireFragmentManager().beginTransaction()
                // changingOfWorkout = ChangingOfWorkoutFragment(listWorkout[position])
                // transaction.replace(R.id.fragment_holder, changingOfWorkout)
                //     .addToBackStack("Check")
                //     .commit()
            }
        })
        binding.workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutRecyclerView.adapter = adapter
    }

    private fun init() {

        //val firebaseUserId = loginViewModel.userId
        //     val firestoreDataBase =
        //         FirebaseFirestore.getInstance()
        //             .collection("Users")
        //             .document(Firebase.auth.currentUser!!.uid)
        //             .collection("Workouts")

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

//        binding.apply {
//            createNewWorkoutFlActButton.setOnClickListener {
//                  Toast.makeText(
//                      context,
//                      "Добавить новую тренировку",
//                      Toast.LENGTH_SHORT
//                  ).show()
//                val transaction = requireFragmentManager().beginTransaction()
//                //workoutViewModel.changedWorkout.value = Workout()
//                transaction.replace(R.id.fragment_holder, creatingOfWorkoutFragment)
//                    .addToBackStack("Check")
//                    .commit()
//            }
//            firestoreDataBase.addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("TAG", "Listen failed.", e)
//                    Log.e("TAG", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//                Log.w("TAG", "Listen NOT failed.", e)
//                Log.e("TAG", "Listen NOT failed.", e)
//                Toast.makeText(context, "Ошибки нет", Toast.LENGTH_SHORT).show()
//                //listWorkout = mutableListOf()
//                adapter.clear()
//                for (doc in value!!) {
//                    val workout: Workout = doc.toObject(Workout::class.java)
//                    adapter.addWorkout(workout)
//                    //listWorkout.add(workout)
//                }
//            }
//            exerciseViewModel.changedWorkout.observe(activity as LifecycleOwner) {
//                if (it != null) {
//                    val firestoreDataBase = FirebaseFirestore.getInstance()
//                        .collection("Users")
//                        .document(Firebase.auth.currentUser!!.uid)
//                        .collection("Workouts")
//                        .document(it.toString())
//                    firestoreDataBase.set(it)
//                }
//            }
//            exerciseViewModel.createdWorkout.observe(activity as LifecycleOwner) {
//                if (it != null) {
//                    //adapter.addWorkout(it)
//                    val firestoreDataBase = FirebaseFirestore.getInstance()
//                        .collection("Users")
//                        .document(Firebase.auth.currentUser!!.uid)
//                        .collection("Workouts")
//                        .document(it.toString())
//                      val checker = firestoreDataBase.get().getResult()?.exists()
//   //                      if(checker != null){
//                          if(checker){
//                              firestoreDataBase.update(it)
//                          }
//                      }
//                    firestoreDataBase.set(it)
//                } else {
//                    Toast.makeText(context, "Ошибка при добавлении тренировки", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }

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
        fun newInstance() = WorkoutsFragment()
    }
}