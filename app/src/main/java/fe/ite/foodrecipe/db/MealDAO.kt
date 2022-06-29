package fe.ite.foodrecipe.db

import androidx.lifecycle.LiveData
import androidx.room.*
import fe.ite.foodrecipe.Domain.Meal

@Dao
interface MealDAO {

    /*
    this function upsert is to insert on the existed data in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

     @Delete
     suspend fun delete(meal: Meal)

     @Query("SELECT * FROM mealRecipe")
     fun getAllMeal():LiveData<List<Meal>>

    @Query("SELECT * FROM mealRecipe order by idMeal asc")
     fun getAllSavedMeals():Meal

}
