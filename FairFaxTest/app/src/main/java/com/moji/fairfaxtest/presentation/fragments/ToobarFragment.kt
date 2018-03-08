package com.moji.fairfaxtest.presentation.fragments

import android.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by moji on 7/3/18.
 */

abstract class ToolbarFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        if(fragmentManager.backStackEntryCount > 0) {
            toolbar_btn_back.visibility = View.VISIBLE
        }
        toolbar_btn_back.setOnClickListener({activity.onBackPressed()})
    }

    protected fun setToolbarTitle(title : String){
        toolbar_tv_title.text = title
    }

}