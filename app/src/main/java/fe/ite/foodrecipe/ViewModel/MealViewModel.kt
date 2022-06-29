package fe.ite.foodrecipe.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fe.ite.foodrecipe.db.MealDatabase
import fe.ite.foodrecipe.Domain.Meal
import fe.ite.foodrecipe.Domain.MealList
import fe.ite.foodrecipe.Retrofit.RetrofitInstance
import fe.ite.foodrecipe.db.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {
    private var mealDetailLiveData= MutableLiveData<Meal>()

    fun mealDetail(id:String){
        RetrofitInstance.api.getDetailMeal(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if(response.body()!=null){
                        mealDetailLiveData.value=response.body()!!.meals[0]
                    }
                else{
                    return
                    }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealDetail",t.message.toString())
            }
        })
    }

    fun observeMealDetailLiveData():LiveData<Meal>{
        return mealDetailLiveData
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}