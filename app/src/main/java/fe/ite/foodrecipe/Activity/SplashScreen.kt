package fe.ite.foodrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import fe.ite.foodrecipe.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root);
        Handler().postDelayed({
            val intent=Intent(this@SplashScreen,MainActivity::class.java)
            startActivity(intent)
        },
        300)
    }
}