package com.moji.fairfaxtest.presentation.presenters

import com.moji.fairfaxtest.presentation.Listeners.RestListener

/**
 * Created by moji on 20/2/18.
 * ------------------------------
 * presenters are to handle all possible situation when calling an API
 * and separate the presentation concern from the data layer
 * and provide a neat an reliable class to be used within presentation layer
 */

interface Presenter<in T : RestListener> {

    fun attachListener(RestListener: T)

}
