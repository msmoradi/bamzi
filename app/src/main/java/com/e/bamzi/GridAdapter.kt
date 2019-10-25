package com.e.bamzi

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.widget.TextView


class GridAdapter(
    private val mContext: Context,
    private val numbers: List<Char>,
    private val typeface: Typeface
) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val gridView: View

        if (convertView == null) {
            // get layout from xml file
            gridView = inflater.inflate(R.layout.grid_item, null);

            // pull views
            var letterView = gridView.findViewById(R.id.grid_item_txv) as TextView
            letterView.text = numbers.get(position).toString()
            letterView.typeface = typeface

        } else {
            gridView = convertView
        }
        return gridView
    }


    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount() = numbers.size
}