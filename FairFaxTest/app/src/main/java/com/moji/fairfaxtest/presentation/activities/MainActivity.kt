package com.moji.fairfaxtest.presentation.activities

import android.os.Bundle
import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.presentation.fragments.MainFragment
import com.moji.fairfaxtest.presentation.fragments.ToolbarFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun getInitiallyDisplayedFragment(): ToolbarFragment {
        return MainFragment()
    }

}
