package fe.ite.foodrecipe.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Domain.Category
import fe.ite.foodrecipe.Domain.CategoryList
import fe.ite.foodrecipe.databinding.CategoryViewHolderBinding
import fe.ite.foodrecipe.databinding.PopularItemHolderBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList= ArrayList<Category>()
     lateinit var categoryClick:((Category)->Unit)

    fun setCategoryList(categoryList: ArrayList<Category>){
        this.categoryList=categoryList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(var binding: CategoryViewHolderBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.categoryImage)
        holder.binding.categoryName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            categoryClick.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}