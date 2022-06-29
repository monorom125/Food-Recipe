package fe.ite.foodrecipe.fragment.ButtomShetFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fe.ite.foodrecipe.Activity.MainActivity
import fe.ite.foodrecipe.Activity.MealDetail
import fe.ite.foodrecipe.ViewModel.HomeViewModel
import fe.ite.foodrecipe.databinding.FragmentButtomShetBinding
import fe.ite.foodrecipe.fragment.HomeFragment

private const val MEAL_ID= "param1"

class ButtomShetFragment : BottomSheetDialogFragment() {
    var mealId:String?=null
    var mealName:String?=null
    var mealThumb:String?=null

    private lateinit var binding:FragmentButtomShetBinding
    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            mealId=it?.getString(MEAL_ID)
        }
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentButtomShetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewModel.getMealById(it)

        }
        observeBtmMealLiveData()
        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener{
            if(mealName!=null && mealThumb!=null){
                val intent=Intent(context,MealDetail::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_thumb,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBtmMealLiveData() {
        viewModel.observeBtmShetMeal().observe(viewLifecycleOwner, Observer {
            meal->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imageButtomShet)
            binding.btmArea.text=meal.strArea
            binding.btmCategory.text=meal.strCategory
            binding.btmMealName.text=meal.strMeal
            mealName=meal.strMeal
            mealThumb=meal.strMealThumb

        })
    }

    companion object{
        @JvmStatic
        fun newInstance(param1:String)=
            ButtomShetFragment().apply {
                arguments=Bundle().apply {
                    putString(MEAL_ID,param1)
                }
            }
    }
}
