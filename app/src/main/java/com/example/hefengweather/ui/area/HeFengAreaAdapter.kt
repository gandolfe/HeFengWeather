package com.example.hefengweather.ui.area

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.hefengweather.data.model.area.AreaData
import com.example.hefengweather.databinding.HefengAreaSimpleItemBinding

class HeFengAreaAdapter(context: Context, val resourceID: Int,val dataList: ArrayList<AreaData>) : ArrayAdapter<AreaData>(context, resourceID, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bind: HefengAreaSimpleItemBinding?
        val view = if (convertView == null) {
            val v = LayoutInflater.from(context).inflate(resourceID, parent, false)
            bind = DataBindingUtil.bind(v)
            v.tag = bind
            v
        } else {
            bind = convertView.tag as HefengAreaSimpleItemBinding
            convertView
        }
        bind?.data = dataList[position]

        return view
    }
}