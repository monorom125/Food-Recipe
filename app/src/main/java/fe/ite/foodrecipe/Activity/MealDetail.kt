package fe.ite.foodrecipe.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import fe.ite.foodrecipe.db.MealDatabase
import fe.ite.foodrecipe.Domain.Meal
import fe.ite.foodrecipe.R
import fe.ite.foodrecipe.ViewModel.MealMVVMFactory
import fe.ite.foodrecipe.ViewModel.MealViewModel
import fe.ite.foodrecipe.databinding.ActivityMealDetialBinding
import fe.ite.foodrecipe.fragment.HomeFragment

class MealDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetialBinding;
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String
    private var favoriteMeal:Meal?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealDetialBinding.inflate(layoutInflater)
      //  mealMvvm=ViewModelProvider(this)[MealViewModel::class.java]
        val mealDatabase= MealDatabase.getInstance(this)
        val viewModelFactory =MealMVVMFactory(mealDatabase)
        mealMvvm= ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        setContentView(binding.root)
        loadingCases()
        getDataFromRandomMeal()
        setDataInView()
        mealMvvm.mealDetail(mealId)
        observeMealData()
        clickOnYoutube()
        onFavoriteClick()
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun onFavoriteClick() {
        binding.love.setOnClickListener {
           saveMeal()
            Snackbar.make( findViewById(android.R.id.content),
            "Meal saved",Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun saveMeal(){
        favoriteMeal?.let {
            mealMvvm.insertMeal(it)
        }
    }

    private fun observeMealData() {
        mealMvvm.observeMealDetailLiveData().observe(this
        ) { t ->
            onResponse()
            favoriteMeal=t
            binding.mealCategory.text = "Category: ${t!!.strCategory}"
            binding.area.text = "Area: ${t!!.strArea}"
            binding.recipe.text = t.strInstructions
            youtubeLink= t.strYoutube.toString()
        }
    }
    private fun setDataInView() {
        binding.collapspringBar.title=mealName
        binding.collapspringBar.setCollapsedTitleTextColor( resources.getColor(R.color.white ))
        binding.collapspringBar.setExpandedTitleColor( resources.getColor(R.color.white ))
        Glide.with(this)
            .load(mealThumb)
            .into(binding.mealThumb)

    }

    private fun getDataFromRandomMeal() {
        val intent =intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_thumb)!!

    }
    private fun loadingCases(){
        binding.progress.visibility= View.VISIBLE
        binding.mealCategory.visibility= View.INVISIBLE
        binding.love.visibility= View.INVISIBLE
        binding.instruction.visibility= View.INVISIBLE
        binding.area.visibility= View.INVISIBLE
        binding.youtube.visibility= View.INVISIBLE
    }

    private fun onResponse(){
        binding.progress.visibility= View.INVISIBLE
        binding.mealCategory.visibility= View.VISIBLE
        binding.love.visibility= View.VISIBLE
        binding.instruction.visibility= View.VISIBLE
        binding.area.visibility= View.VISIBLE
        binding.youtube.visibility= View.VISIBLE

    }
    //listen to youtube image was  clicked
    private fun clickOnYoutube(){
        binding.youtube.setOnClickListener {
            val youtubeInten= Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(youtubeInten);

        }
    }

}