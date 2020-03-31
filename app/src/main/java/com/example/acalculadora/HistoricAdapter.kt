package com.example.acalculadora

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_expression.view.*

class HistoricAdapter (
    context: Context,
    private val layout: Int,
    items: MutableList<Operation>
) : ArrayAdapter<Operation> (context, layout, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layout, parent, false)
        view.text_expression.text = getItem(position)?.expression
        view.text_result.text = getItem(position)?.result.toString()
        return view
    }

}