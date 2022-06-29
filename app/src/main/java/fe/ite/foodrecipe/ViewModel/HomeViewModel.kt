package fe.ite.foodrecipe.ViewModel

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fe.ite.foodrecipe.Domain.*
import fe.ite.foodrecipe.Retrofit.MealApi
import fe.ite.foodrecipe.Retrofit.RetrofitInstance
import fe.ite.foodrecipe.db.MealDatabase
import fe.ite.foodrecipe.fragment.SearchMealFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler

class HomeViewModel(
    private val mealDatabase: MealDatabase
): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeal()
    private var btmShetMealLiveData=MutableLiveData<Meal>()
    private val searchMealLivedata=MutableLiveData<List<Meal>>()
    private var currentIndex = 0
    private val delay = 3000L


    fun searchMeal(search: String, context: SearchMealFragment){
        RetrofitInstance.api.getSearchMeals(search).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.isSuccessful) {
                    val meallist=response.body()?.meals
                    meallist?.let {
                        searchMealLivedata.postValue(it)
                    }
                }
                  else{
                    Toast.makeText(context.requireContext(),"You connect may be error please try again",Toast.LENGTH_SHORT)
                  }
                }


            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Toast.makeText(context.requireContext(),t.message.toString(),Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
    fun observeSearchMeal() :LiveData<List<Meal>>{
        return searchMealLivedata
    }

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())

            }
        })

    }

    /**
     * this function is to read the value(LIVEDATA)
     */
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }
    fun getMealById(id:String){
        RetrofitInstance.api.getDetailMeal(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal= response.body()?.meals?.first()
                meal?.let {
                    meal->
                    btmShetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home",t.message.toString())
            }

        })
    }
    fun getPopularMeal() {
        RetrofitInstance.api.getPopularMeal("Seafood").enqueue(object : Callback<CategoryMealList> {
            override fun onResponse(
                call: Call<CategoryMealList>,
                response: Response<CategoryMealList>
            ) {
                if (response.body() != null) {
                    popularMealLiveData.value = response.body()!!.meals
                } else {
                    return
                }

            }

            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }
    fun observeBtmShetMeal(): LiveData<Meal>{
        return btmShetMealLiveData
    }

    fun observePopularItemLiveData(): LiveData<List<CategoryMeals>> {
        return popularMealLiveData
    }

    fun getCategory() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoryLiveData.value = response.body()!!.categories

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())

            }
        })
    }

    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoryLiveData
    }

    fun observeFavoriteMealLiveData(): LiveData<List<Meal>> {
        return favoriteMealLiveData
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }

    }
}
