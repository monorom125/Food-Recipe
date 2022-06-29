package fe.ite.foodrecipe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import fe.ite.foodrecipe.Activity.MainActivity
import fe.ite.foodrecipe.Activity.MealsByCategory
import fe.ite.foodrecipe.Adaptor.CategoryAdapter
import fe.ite.foodrecipe.Domain.Category
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.FragmentCategoryBinding
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.Category_Name

// TODO: Rename parameter arguments, choose names that match

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: HomeViewModel
     lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        this.categoryAdapter= CategoryAdapter()

        }

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=FragmentCategoryBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCategory()
        initCategory()
        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryAdapter.categoryClick={
            val intent=Intent(activity,MealsByCategory::class.java)
            intent.putExtra(Category_Name,it.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategory() {
            viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { category ->
                categoryAdapter.setCategoryList(category as ArrayList<Category>)

            }
    }
    private fun initCategory() {
        binding.areaRec.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }


}


