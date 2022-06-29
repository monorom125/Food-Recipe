package fe.ite.foodrecipe.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fe.ite.foodrecipe.db.MealDatabase

class HomeMVVMFactory(
   private val mealDatabase: MealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}