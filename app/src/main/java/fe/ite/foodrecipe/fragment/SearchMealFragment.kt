package fe.ite.foodrecipe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import fe.ite.foodrecipe.Activity.MainActivity
import fe.ite.foodrecipe.Activity.MealDetail
import fe.ite.foodrecipe.Adaptor.SearchMealsAdapter
import fe.ite.foodrecipe.R
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.FragmentSearchMealBinding
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_ID
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_NAME
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_thumb


class SearchMealFragment : Fragment() {
     lateinit var binding: FragmentSearchMealBinding
     lateinit var viewModel:HomeViewModel
     lateinit var searchAdapter: SearchMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        searchAdapter= SearchMealsAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentSearchMealBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiSearchRecycleView()
        observeSearchMealLiveData()
        binding.cancel.setOnClickListener{
            findNavController().navigate(R.id.action_searchMealFragment_to_homeFragment)
        }
        searchMeals()
        onSearchMealClick()

    }
    // this function is occurred  when clicking on result searching meal happen, and it intent to mealDetail Activity
    private fun onSearchMealClick() {
        searchAdapter.onSearchMealClick={
            val intent=Intent(activity,MealDetail::class.java)
            intent.apply {
                putExtra(MEAL_ID,it.idMeal)
                putExtra(MEAL_NAME,it.strMeal)
                putExtra(MEAL_thumb,it.strMealThumb)
            }
            startActivity(intent)
        }
    }

    //search meals
    private fun searchMeals(){
        val search:SearchView=binding.searchView
        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                if (query != null) {
                    viewModel.searchMeal(query,this@SearchMealFragment)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    viewModel.searchMeal(newText,this@SearchMealFragment)
                }
               return false
            }

        })

    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchMeal().observe(viewLifecycleOwner, Observer {
            meal->
            searchAdapter.setSearchMeal(meal)
        })
    }

    private fun intiSearchRecycleView() {
        binding.searchRec.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchAdapter
        }

    }
}