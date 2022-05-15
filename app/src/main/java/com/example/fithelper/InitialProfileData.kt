package com.example.fithelper

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.fithelper.databinding.FragmentInitialProfileDataBinding
import com.example.fithelper.screens.mainActivity.MainActivity
import com.example.fithelper.services.AuthenticationService
import com.example.fithelper.services.UserService


class InitialProfileData : Fragment() {

    lateinit var binding: FragmentInitialProfileDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitialProfileDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmProfileDataButton.setOnClickListener{
            UserService.updateProfile { builder ->
                builder.displayName = binding.nameEditText.text.toString()

                //TODO("Photo Uri")
                builder.build()
            }
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.skipTextView.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

//        binding.imageView.setOnClickListener{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_DENIED){
//                    //permission denied
//                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
//                    //show popup to request runtime permission
//                    requestPermissions(permissions, PERMISSION_CODE);
//                }
//                else{
//                    //permission already granted
//                    pickImageFromGallery();
//                }
//            }
//            else{
//                //system OS is < Marshmallow
//                pickImageFromGallery();
//            }
//        }
    }

//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE)
//    }
//
//    private fun pickImageFromGallery() {
//        //Intent to pick image
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }
//
//    //handle requested permission result
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size >0 && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED){
//                    //permission from popup granted
//                    pickImageFromGallery()
//                }
//                else{
//                    //permission from popup denied
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    companion object{
//        private val IMAGE_PICK_CODE = 1000
//        private val PERMISSION_CODE = 1001
//    }
}