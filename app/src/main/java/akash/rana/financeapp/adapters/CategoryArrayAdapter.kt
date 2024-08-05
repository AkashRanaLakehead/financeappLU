package akash.rana.financeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import akash.rana.financeapp.models.Category

class CategoryArrayAdapter(
    context: Context,
    resource: Int,
    objects: Array<Category>
) : ArrayAdapter<Category>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_spinner_item,
            parent,
            false
        )

        (view as TextView).text = getItem(position)?.description
        return view
    }
}