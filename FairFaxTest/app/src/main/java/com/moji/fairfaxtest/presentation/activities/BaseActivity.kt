package com.moji.fairfaxtest.presentation.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.presentation.fragments.ToolbarFragment

/** Base Activity for the app
 * We use fragment for transition between contents to ensure a smoother user experience **/
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        // set initial fragment
        if (savedInstanceState == null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_place_holder, getInitiallyDisplayedFragment())
            fragmentTransaction.commit()
        }
    }

    // activities that extends BaseActivity should determine their initially displayed fragment
    abstract fun getInitiallyDisplayedFragment() : ToolbarFragment

}
