package com.example.easyshop.viewModel

import androidx.lifecycle.ViewModel
import com.example.easyshop.Model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore


class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestor = Firebase.firestore

    fun login(email: String,password: String,onResult : (Boolean,String?) -> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onResult(true,null)
                }else{
                    onResult(false,it.exception?.localizedMessage)
                }
            }

    }

    fun signup(email : String , name : String , password : String , onResult : (Boolean,String?) -> Unit){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    var userid = it.result?.user?.uid

                    val userModel = UserModel(name, email,userid!!)
                    firestor.collection("users").document(userid)
                        .set(userModel)
                        .addOnCompleteListener {dbTask->
                            if (dbTask.isSuccessful){
                                onResult(true,null)
                            }else{
                                onResult(false,"Something went wrong")
                            }
                        }
                }else{
                    onResult(false,it.exception?.localizedMessage)
                }
            }
    }
}