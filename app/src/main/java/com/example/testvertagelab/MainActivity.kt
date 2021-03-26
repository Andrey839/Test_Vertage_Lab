package com.example.testvertagelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.example.testvertagelab.databinding.ActivityMainBinding
import com.example.testvertagelab.networkAPI.Api
import com.example.testvertagelab.networkAPI.PlacesLocations
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var email = ""
    private var listPlaces = arrayListOf<PlacesLocations>()
    private lateinit var myScope: CoroutineScope
    private lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()
        myScope = CoroutineScope(Dispatchers.IO+job)

        getListPlaces()
    }

    private fun getListPlaces() {
        myScope.launch {
            try {
                val resultPlaces = Api.getData.getPlaces()
                listPlaces.addAll(resultPlaces.places)
            } catch (e: Exception) {
                Log.e("tyi", "error request $e")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.loginView.doAfterTextChanged {
            email = it.toString()
        }

        binding.buttonLogin.setOnClickListener {
            if (email.isNotEmpty()) {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("email", email)
                intent.putParcelableArrayListExtra("list",listPlaces)
                startActivity(intent)
            }
        }

        binding.passwordView.doAfterTextChanged {
            if (it.toString().isNotEmpty()) binding.buttonLogin.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}