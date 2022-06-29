package fe.ite.foodrecipe.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.Domain.Meal
import fe.ite.foodrecipe.databinding.MealeByCategoryHolderBinding

class FavoriteMealsAdapter():RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealViewHolder>() {
    private var favoriteMeals: List<Meal> = ArrayList()
    lateinit var onFavoriteMealClick:((Meal)->Unit)


    fun setFavoriteMeal(favoriteMeal:List<Meal> ){
        this.favoriteMeals=favoriteMeal
        notifyDataSetChanged()
    }
    fun getMelaByPosition(position: Int):Meal{
        return favoriteMeals[position]
    }


    class FavoriteMealViewHolder(var binding:MealeByCategoryHolderBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {

        return FavoriteMealViewHolder(MealeByCategoryHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
       val meal = favoriteMeals[position]
        Glide.with(holder.itemView).load(meal.strMealThumb)
            .into(holder.binding.mealCImage)
        holder.binding.foodName.text=meal.strMeal
        holder.itemView.setOnClickListener {
            onFavoriteMealClick.invoke(meal)
        }
    }
    override fun getItemCount(): Int {
       return favoriteMeals.size

    }
}