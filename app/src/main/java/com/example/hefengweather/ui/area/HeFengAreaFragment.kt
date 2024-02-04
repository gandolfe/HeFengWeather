package com.example.hefengweather.ui.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hefengweather.R
import com.example.hefengweather.data.model.area.AreaData
import com.example.hefengweather.util.InjectorUtil

class HeFengAreaFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getHeFengAreaModelFactory()).get(
        HeFengAreaModel::class.java) }

    private lateinit var adapter: ArrayAdapter<AreaData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hefeng_area, container, false)
        val binding = DataBindingUtil.bind<FragmentHefengAreaBindingImpl>(view)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this  // 一定要有这句关联，不然databinding不起作用
        addObserver()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = HeFengAreaAdapter(context!!, R.layout.hefeng_area_simple_item, viewModel.districts)
        heFengAreaList.adapter = adapter
        viewModel.getAreaDataOfProvince()
    }

    fun addObserver() {
        viewModel.dataChange.observe(this, Observer{
            adapter.notifyDataSetChanged()
        })

        viewModel.isShowBack.observe(this, Observer {
            if (it) {
                backButton.visibility = View.VISIBLE
            } else {
                backButton.visibility =View.GONE
            }
        })

        viewModel.selectedArea.observe(this, Observer {
            if (activity is HefengActivity) {
                val weatherActivity = activity as HefengActivity
                weatherActivity.hefengDrawer.closeDrawers()
                weatherActivity.viewModel.areaid = it.adcode
                weatherActivity.viewModel.currentAreaName.value = "当前区域：${it.name}"
                weatherActivity.viewModel.getHefengNow()
            }
        })
    }
}