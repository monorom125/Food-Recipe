package fe.ite.foodrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import fe.ite.foodrecipe.Adaptor.MealByCategoryAdapter
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.ViewModel.CategoryMealsViewModel
import fe.ite.foodrecipe.databinding.ActivityMealsByCategoryBinding
import fe.ite.foodrecipe.fragment.HomeFragment
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_ID
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_NAME
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_thumb

class MealsByCategory : AppCompatActivity() {
     lateinit var binding: ActivityMealsByCategoryBinding
     lateinit var mealmvvm: CategoryMealsViewModel
    private lateinit var mealAdapter: MealByCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealsByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealmvvm=ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        mealAdapter=MealByCategoryAdapter()

        mealmvvm.getMealByCategory(intent.getStringExtra(HomeFragment.Category_Name)!!)
        getObserveMealLiveData()
        mealByCategoryRecycirleView()
        clickOnMeal()
        binding.onback.setOnClickListener {
            finish()
        }
    }

    private fun clickOnMeal() {
        mealAdapter.mealOnClick= {
            val intent = Intent(this,MealDetail::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_NAME,it.strMeal)
            intent.putExtra(MEAL_thumb,it.strMealThumb)
            startActivity(intent);
        }
    }
    private fun getObserveMealLiveData() {
        mealmvvm.observeMealByCategory().observe(this, Observer {
            binding.categoryName.text =("${intent.getStringExtra(HomeFragment.Category_Name)} : ${it.size}")
            mealAdapter.setMealByCategoryList(it as ArrayList<CategoryMeals>)
        })
    }

    private fun mealByCategoryRecycirleView() {
        binding.mealByCategoryRec.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealAdapter
        }

    }
}