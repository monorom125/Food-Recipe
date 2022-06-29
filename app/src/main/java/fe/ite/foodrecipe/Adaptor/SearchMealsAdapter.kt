package fe.ite.foodrecipe.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Domain.Meal
import fe.ite.foodrecipe.databinding.MealeByCategoryHolderBinding

class SearchMealsAdapter():RecyclerView.Adapter<SearchMealsAdapter.FavoriteMealViewHolder>() {
    private var searchMeal: List<Meal> = ArrayList()
    lateinit var onSearchMealClick:((Meal)->Unit)


    fun setSearchMeal(favoriteMeal:List<Meal> ){
        this.searchMeal=favoriteMeal
        notifyDataSetChanged()
    }
    fun getMelaByPosition(position: Int):Meal{
        return searchMeal[position]
    }


    class FavoriteMealViewHolder(var binding:MealeByCategoryHolderBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {

        return FavoriteMealViewHolder(MealeByCategoryHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
       val meal = searchMeal[position]
        Glide.with(holder.itemView).load(meal.strMealThumb)
            .into(holder.binding.mealCImage)
        holder.binding.foodName.text=meal.strMeal
        holder.itemView.setOnClickListener {
            onSearchMealClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
       return searchMeal.size

    }
}