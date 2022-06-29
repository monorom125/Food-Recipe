package fe.ite.foodrecipe.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import fe.ite.foodrecipe.Activity.MainActivity
import fe.ite.foodrecipe.Activity.MealDetail
import fe.ite.foodrecipe.Adaptor.FavoriteMealsAdapter
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.FragmentFavoriteBinding
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_ID
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_NAME
import fe.ite.foodrecipe.fragment.HomeFragment.Companion.MEAL_thumb

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class FavoriteFragment : Fragment() {
    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealAdapter:FavoriteMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavoriteBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoriteRecycleView()
        observeFavorites()
        val itemTouchHelper= object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val myFavoriteMeal=favoriteMealAdapter.getMelaByPosition(position)
                viewModel.deleteMeal(myFavoriteMeal)
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                        View.OnClickListener {
                        viewModel.insertMeal(myFavoriteMeal)
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFavorite)
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        favoriteMealAdapter.onFavoriteMealClick={ meal ->
            val intent=Intent(activity,MealDetail::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_thumb,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun initFavoriteRecycleView() {
        favoriteMealAdapter= FavoriteMealsAdapter()
        binding.recFavorite.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoriteMealAdapter
        }

    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealLiveData().observe(requireActivity(), Observer {
            meals->
            favoriteMealAdapter.setFavoriteMeal(meals)
        })
    }

}
