package fe.ite.foodrecipe.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fe.ite.foodrecipe.db.MealDatabase

class MealMVVMFactory(
   private val mealDatabase: MealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}