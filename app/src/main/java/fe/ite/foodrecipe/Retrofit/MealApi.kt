package fe.ite.foodrecipe.Retrofit

import fe.ite.foodrecipe.Domain.CategoryList
import fe.ite.foodrecipe.Domain.CategoryMealList
import fe.ite.foodrecipe.Domain.MealList
import fe.ite.foodrecipe.fragment.SearchMealFragment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getDetailMeal(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(@Query("c")popularMeal:String):Call<CategoryMealList>

    @GET("categories.php")
    fun getCategory():Call<CategoryList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c")mealsByCategory:String):Call<CategoryMealList>

    @GET("search.php?")
    fun getSearchMeals(@Query("s") searchQuery: String):Call<MealList>

}