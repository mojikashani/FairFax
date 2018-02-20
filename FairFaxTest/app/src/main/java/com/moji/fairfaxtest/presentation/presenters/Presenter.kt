package com.moji.fairfaxtest.presentation.presenters

import com.moji.fairfaxtest.presentation.Listeners.RestListener

/**
 * Created by moji on 20/2/18.
 */

interface Presenter<in T : RestListener> {

    fun attachListener(RestListener: T)
    fun destroy()

}
