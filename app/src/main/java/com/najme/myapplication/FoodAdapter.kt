package com.najme.duniFood

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.dunijet.dunifoodsimple.R


class FoodAdapter(private val foodData: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)
        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            txtSubject.text = foodData[position].txtSubject
            txtCity.text = foodData[position].txtCity
            txtDistance.text = foodData[position].txtDistance + " miles from you"
            txtPrice.text = "$" + foodData[position].txtPrice + "VIP"
            txtRating.text = foodData[position].numOfRating.toString()
            Glide
                .with(context)
                .load(foodData[position].urlImage)
                .into(imgMain)
            itemView.setOnClickListener {
                foodEvents.onFoodClicked(foodData[adapterPosition], adapterPosition)
            }
            itemView.setOnLongClickListener {
                foodEvents.onFoodLongClicked(foodData[adapterPosition], adapterPosition)
                true
            }

        }

    }
        fun addFood(newFood: Food) {
            // add food to list :
            foodData.add(0, newFood)
            notifyItemInserted(0)
        }

        fun removeFood(oldFood: Food, oldPosition: Int) {

            // remove item from list :
            foodData.remove(oldFood)
            notifyItemRemoved(oldPosition)

        }

        fun updateFood(newFood: Food, position: Int) {

            // update item from list :
            foodData.set(position, newFood)
            notifyItemChanged(position)

        }

        fun setData(newList: ArrayList<Food>) {

            // set new data to list :
            foodData.clear()
            foodData.addAll(newList)

            notifyDataSetChanged()

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return foodData.size
    }
    interface FoodEvents {
        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, pos: Int)
    }
}

