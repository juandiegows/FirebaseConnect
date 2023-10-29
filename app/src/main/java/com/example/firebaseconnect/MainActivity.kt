package com.example.firebaseconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseconnect.databinding.ActivityMainBinding
import com.example.firebaseconnect.models.Gender
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var  binding : ActivityMainBinding
     lateinit var firedatabase : FirebaseDatabase
     lateinit var  generoRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firedatabase = FirebaseDatabase.getInstance()
        generoRef = firedatabase.getReference("genders")
        setContentView(binding.root)
        delete("-NhsvyGpTxM3ANSgYxwX")
    }
    fun create( gender : Gender){
        var id = generoRef.push().key
        gender.id = id ?: ""
        generoRef.child(id ?: "").setValue(gender)
    }
    fun read(){
        generoRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listGenders : MutableList<Gender> = mutableListOf()

                snapshot.children.forEach {
                    listGenders.add(it.getValue(Gender::class.java) ?: Gender("",""))
                }
                Toast.makeText(this@MainActivity, listGenders.size.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun update(gender: Gender){
        var id = gender.id
        var genderUpdate = HashMap<String, Any>()
        genderUpdate["name"] = gender.name
        generoRef.child(id).setValue(gender)
    }

    fun delete(id : String){
        generoRef.child(id).removeValue()
    }
}