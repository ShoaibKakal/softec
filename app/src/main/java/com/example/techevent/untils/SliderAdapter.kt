package com.example.techevent.untils

import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.techevent.R
import com.example.techevent.homescreen.model.SlideItem
import com.google.android.material.slider.Slider
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter(val sliderItems: ArrayList<SlideItem>, val viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item_container, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderAdapter.SliderViewHolder, position: Int) {
        holder.bindData(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }


    class SliderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        lateinit var imageView: RoundedImageView

        fun bindData(slider: SlideItem) {
            imageView = view.findViewById(R.id.imageSlide)
            imageView.setImageResource(slider.image)
        }

    }

    private val runnable = Runnable {
        kotlin.run {
            sliderItems.addAll(sliderItems)
            notifyDataSetChanged()
        }
    }


}