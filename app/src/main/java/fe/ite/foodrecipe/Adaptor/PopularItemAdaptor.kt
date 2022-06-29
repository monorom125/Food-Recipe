package fe.ite.foodrecipe.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.databinding.PopularItemHolderBinding

class PopularItemAdaptor():RecyclerView.Adapter<PopularItemAdaptor.PopularItemHolder>() {
    private var mealList=ArrayList<CategoryMeals>()
     lateinit var onItemClick:((CategoryMeals)->Unit)
     var longClickItem:((CategoryMeals)->Unit)?=null

    fun setMeals(mealList:ArrayList<CategoryMeals>){
        this.mealList=mealList
        notifyDataSetChanged()

    }
    class PopularItemHolder( var binding: PopularItemHolderBinding):RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemHolder {
        return PopularItemHolder(PopularItemHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularItemHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularItemImage)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener() {
            longClickItem?.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}