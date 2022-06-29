package fe.ite.foodrecipe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fe.ite.foodrecipe.Activity.MainActivity
import fe.ite.foodrecipe.Domain.Meal
import fe.ite.foodrecipe.Activity.MealDetail
import fe.ite.foodrecipe.Activity.MealsByCategory
import fe.ite.foodrecipe.Adaptor.CategoryAdapter
import fe.ite.foodrecipe.Adaptor.PopularItemAdaptor
import fe.ite.foodrecipe.Domain.Category
import fe.ite.foodrecipe.Domain.CategoryMeals
import fe.ite.foodrecipe.R
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.FragmentHomeBinding
import fe.ite.foodrecipe.fragment.ButtomShetFragment.ButtomShetFragment
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
     private lateinit var binding:FragmentHomeBinding
     private lateinit var viewModel: HomeViewModel
     private lateinit var randomMeal:Meal
     private lateinit var popularAdapter:PopularItemAdaptor
     private lateinit var categoryAdaptor: CategoryAdapter
     companion object{
         const val MEAL_ID= "fe.ite.foodrecipe.fragment.idMeal"
         const val MEAL_NAME= "fe.ite.foodrecipe.fragment.nameMeal"
         const val MEAL_thumb= "fe.ite.foodrecipe.fragment.thumbMeal"
         const val Category_Name="fe.ite.foodrecipe.fragment.categoryName"

     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularAdapter= PopularItemAdaptor()
        viewModel=(activity as MainActivity).viewModel
        this.categoryAdaptor= CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false  )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = viewModel
        viewModel.getRandomMeal()
        observeRandomMeal()
        homeImageCLick()
        viewModel.getPopularMeal()
        popularRecycircleView()
        observePopularMeal()
        popularItemOcClick()
        popularItemLogClick()
        binding.homeSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_searchMealFragment)
        }

        viewModel.getCategory()
        observeCategory()
        categoryRecycleView()
        onCategoryClick()



    }

    private fun popularItemLogClick() {
        popularAdapter.longClickItem={meal->
            val btmShetFragment= ButtomShetFragment.newInstance(meal.idMeal)
            btmShetFragment.show(childFragmentManager,"Meal Information")
        }
    }

    private fun categoryRecycleView() {
        binding.recCategory.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdaptor
        }
    }

    private fun onCategoryClick() {
        categoryAdaptor.categoryClick={ category->
            val intent=Intent(activity,MealsByCategory::class.java)
            intent.putExtra(Category_Name,category.strCategory)
            startActivity(intent)

        }
    }

    private fun observeCategory() {

        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) {
                category->
                categoryAdaptor.setCategoryList(category as ArrayList<Category>)

        }
    }



    private fun popularItemOcClick() {
        popularAdapter.onItemClick={ meal->
            val intent=Intent(activity,MealDetail::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_thumb,meal.strMealThumb)
            startActivity(intent)


        }
    }

    private fun popularRecycircleView() {
        binding.recPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter =popularAdapter
        }
    }

    private fun observePopularMeal() {

        viewModel.observePopularItemLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularAdapter.setMeals(mealList = mealList as ArrayList<CategoryMeals> /* = java.util.ArrayList<fe.ite.foodrecipe.Domain.CategoryMeals> */)
        }
    }

    private fun homeImageCLick() {
       binding.randomMealImage.setOnClickListener {
           val inten = Intent(activity, MealDetail::class.java)
           inten.putExtra(MEAL_ID,randomMeal.idMeal)
           inten.putExtra(MEAL_NAME,randomMeal.strMeal)
           inten.putExtra(MEAL_thumb,randomMeal.strMealThumb)
           startActivity(inten)
       }
    }

    /**
     * to read data for livedata in viewModel to the banner in HomeFragment.
     */
    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.randomMealImage)
            this.randomMeal = meal
        }
    }
}