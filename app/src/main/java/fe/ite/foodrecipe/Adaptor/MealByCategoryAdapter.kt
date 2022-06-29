package fe.ite.foodrecipe.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Domain.Category
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.databinding.MealeByCategoryHolderBinding

class MealByCategoryAdapter():RecyclerView.Adapter<MealByCategoryAdapter.MealByCategoryHolder>() {
     private var mealByCategoryList= ArrayList<CategoryMeals>()
     lateinit var mealOnClick: ((CategoryMeals)->Unit)

    fun setMealByCategoryList(mealByCategoryList:ArrayList<CategoryMeals>){
        this.mealByCategoryList=mealByCategoryList
         notifyDataSetChanged()
    }

    class MealByCategoryHolder(var binding:MealeByCategoryHolderBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealByCategoryHolder {

        return MealByCategoryHolder(MealeByCategoryHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MealByCategoryHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealByCategoryList[position].strMealThumb)
            .into(holder.binding.mealCImage)
         holder.binding.foodName.text=mealByCategoryList[position].strMeal
        holder.itemView.setOnClickListener {
            mealOnClick.invoke(mealByCategoryList[position])
        }
    }
    override fun getItemCount(): Int {
       return mealByCategoryList.size

    }
}