package com.najme.duniFood

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.PhoneAccount.builder
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najme.myapplication.MyDatabase
import com.najme.myapplication.foodDao
import ir.dunijet.dunifoodsimple.databinding.ActivityMainBinding
import ir.dunijet.dunifoodsimple.databinding.DialogAddNewItemBinding
import ir.dunijet.dunifoodsimple.databinding.DialogDeleteItemBinding
import ir.dunijet.dunifoodsimple.databinding.DialogUpdateItemBinding
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder
import java.util.stream.LongStream.builder
import java.util.stream.Stream.builder
const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var foodDao: foodDao
    private lateinit var foodAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foodDao = MyDatabase.getDatabase(this).foodDao
        val sharedPreferences = getSharedPreferences("first_run", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run", true)) {
            FirstRun()
            sharedPreferences.edit().putBoolean("first_run", false).apply()
        }

        showAllData()



        binding.btnAddNewFood.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogBinding.dialogBtnDone.setOnClickListener {
                if (
                    dialogBinding.dialogEdtNameFood.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0
                ) {

                    val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()

                    val randomForUrl = (0 until 12).random()
                    val urlPic = BASE_URL_IMAGE + randomForUrl.toString() + ".jpg"

                    val newFood = Food(
                        txtSubject = txtName,
                        txtPrice = txtPrice,
                        txtDistance = txtDistance,
                        txtCity = txtCity,
                        urlImage = urlPic,
                        numOfRating = txtRatingNumber,
                        rating = ratingBarStar
                    )
                    foodAdapter.addFood(newFood)
                    foodDao.insertOrUpdate(newFood)

                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)


                } else {

                    Toast.makeText(this, "لطفا همه مقادیر را وارد کنید :)", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        binding.edtMain.addTextChangedListener { editTextInput ->
            searchOnDatabase(editTextInput!!.toString())
    }}

    private fun searchOnDatabase(editTextInput: String) {
        if (editTextInput.isNotEmpty()) {

            // filter data   'hamid'
            val searchedData = foodDao.searchFoods(editTextInput)
            foodAdapter.setData(searchedData as ArrayList<Food>)

        val foodData = foodDao.getAllFoods()

        } else {

            // show all data :
            val data = foodDao.getAllFoods()
            foodAdapter.setData(ArrayList(data))

        }
    }

    private fun FirstRun() {

        val foodList = listOf(
            Food(
                txtSubject = "Hamburger",
                txtPrice = "15",
                txtDistance = "3",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtCity = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = 10,
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = 30,
                rating = 2f
            ),
            Food(
                txtSubject = "pizza",
                txtPrice = "10",
                txtDistance = "2.5",
                txtCity = "Zahedan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numOfRating = 80,
                rating = 1.5f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtCity = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = 200,
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtCity = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = 50,
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtCity = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = 70,
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtCity = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = 40,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtCity = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = 15,
                rating = 5f
            ),
            Food(
                txtSubject = "Baryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtCity = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = 28,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtCity = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = 27,
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtCity = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = 35,
                rating = 2.5f
            ),
        )
        foodDao.insertAllFoods(foodList)
    }

    private fun showAllData() {
        val foodData = foodDao.getAllFoods()
        foodAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = foodAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        updateDialogBinding.dialogEdtNameFood.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)
        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {
            if (
                updateDialogBinding.dialogEdtNameFood.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0
            ) {

                val txtName = updateDialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()

                // create new food to add to recycler view
                val newFood = Food(
                    id = food.id,
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = food.urlImage,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                foodAdapter.updateFood(newFood, position)
                foodDao.insertOrUpdate(newFood)

                dialog.dismiss()

            } else {
                Toast.makeText(this, "لطفا همه مقادیر را وارد کن :)", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onFoodLongClicked(food: Food, pos: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeletBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeletBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogDeletBinding.dialogBtnDeleteCancel.setOnClickListener { dialog.dismiss() }
        dialogDeletBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            dialog.dismiss()
            foodAdapter.removeFood(food, pos)
            foodDao.deleteFood(food)


        }


    }

}