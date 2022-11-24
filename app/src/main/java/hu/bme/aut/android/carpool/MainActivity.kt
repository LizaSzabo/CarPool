package hu.bme.aut.android.carpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.carpool.data.util.FirebaseAuthentication
import hu.bme.aut.android.carpool.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        setTheme(R.style.Theme_CarPool)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CarPoolApplication.firebaseAuth = FirebaseAuthentication()
    }
}