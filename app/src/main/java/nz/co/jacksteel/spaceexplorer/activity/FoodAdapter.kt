package nz.co.jacksteel.spaceexplorer.activity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.CrewMember
import nz.co.jacksteel.spaceexplorer.model.FoodItem

class FoodAdapter(
    private val data: MutableList<FoodItem>,
    private val crew: List<CrewMember>,
    private val useFoodItemCallback: (CrewMember, FoodItem) -> Boolean
) : RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {

    private lateinit var applicationContext: Context

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.plain_text_view, parent, false) as TextView

        applicationContext = parent.context

        val holder = MyViewHolder(textView)

        textView.setOnClickListener {
            clickListener(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = applicationContext.getString(R.string.food_lunch)
    }

    override fun getItemCount(): Int = data.size

    private fun clickListener(selectedIndex: Int) {
        crewSelectorDialog(applicationContext, crew) {
            if (useFoodItemCallback(it, data[selectedIndex])) {
                data.removeAt(selectedIndex)
                notifyItemRemoved(selectedIndex)
            }
        }
    }
}