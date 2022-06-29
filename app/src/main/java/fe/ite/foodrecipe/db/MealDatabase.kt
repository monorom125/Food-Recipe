package fe.ite.foodrecipe.db

import android.content.Context
import androidx.room.*
import fe.ite.foodrecipe.Domain.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase:RoomDatabase(){
    abstract fun mealDao():MealDAO
    companion object{
        @Volatile
        var INSTANCE:MealDatabase?=null

        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if(INSTANCE==null){
                INSTANCE=Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }

}