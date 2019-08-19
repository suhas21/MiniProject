package com.example.suhas.mini1

import android.view.ViewGroup
import android.widget.ListView

class Helper {
    fun getListViewSize(myListView: ListView) {
        val myListAdapter = myListView.getAdapter()
            ?: //do nothing return null
            return
        var totalHeight = 0
        for (size in 0 until myListAdapter.getCount()) {
            val listItem = myListAdapter.getView(size, null, myListView)
            listItem.measure(0, 0)
            totalHeight += listItem.getMeasuredHeight()
        }
        //setting listview item in adapter
        val params = myListView.getLayoutParams()
        params.height = totalHeight + myListView.getDividerHeight() * (myListAdapter.getCount() - 1)
        myListView.setLayoutParams(params)
        // print height of adapter on log
     //   Log.i("height of listItem:", totalHeight.toString())
    }
}