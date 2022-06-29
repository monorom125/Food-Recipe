package fe.ite.foodrecipe.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fe.ite.foodrecipe.Domain.CategoryMealList
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {

    val mealByCategoryLiveData = MutableLiveData<List<CategoryMeals>>()

    fun getMealByCategory(categoryName:String ){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<CategoryMealList>{
            override fun onResponse(
                call: Call<CategoryMealList>,
                response: Response<CategoryMealList>
            ) {
                response.body()?.let {
                        mealByCategoryLiveData.postValue(it.meals)
                    }
                }
            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                Log.d("MealsByCategory",t.message.toString())
            }
        })
    }
    fun observeMealByCategory():LiveData<List<CategoryMeals>>{
        return mealByCategoryLiveData
    }

}