package fe.ite.foodrecipe.db

import androidx.lifecycle.LiveData
import fe.ite.foodrecipe.Domain.Meal

class Repository(private val mealDAO: MealDAO) {
    val mealList: LiveData<List<Meal>> = mealDAO.getAllMeal()

    suspend fun insertFavoriteMeal(meal: Meal){
        mealDAO.upsert(meal)
    }
    suspend fun deleteFavoriteMeal(meal: Meal){
        mealDAO.delete(meal)
    }
    suspend fun saveFavoriteMeal(id:String): Meal {
        return mealDAO.getAllSavedMeals()
    }


}