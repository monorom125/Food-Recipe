package fe.ite.foodrecipe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import fe.ite.foodrecipe.R
import fe.ite.foodrecipe.ViewModel.HomeMVVMFactory
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.ActivityMainBinding
import fe.ite.foodrecipe.db.MealDatabase

private lateinit var binding :ActivityMainBinding
class MainActivity : AppCompatActivity() {
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance( this)
        val homeMVVMFactory=HomeMVVMFactory(mealDatabase)
        ViewModelProvider(this,homeMVVMFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigation= binding.navBotton
        val navController=Navigation.findNavController(this, R.id.main_fragment);

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }

}


