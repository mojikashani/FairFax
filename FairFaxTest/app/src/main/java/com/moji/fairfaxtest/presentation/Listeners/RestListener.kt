package com.moji.fairfaxtest.presentation.Listeners

/**
 * Created by moji on 20/2/18.
 * -------------------------------
 * list of callbacks needed for all rest api presenters
 */

interface RestListener {
    /**
     * Remove the progress view from the screen. If modal, this will allow interaction with the app to resume. This
     * method should at least hide any error messages now showing.
     */
    fun hideProgress()

    /**
     * Invoked instead of onError when the user turns out not to have permission to do the operation they were
     * attempting.
     */
    fun onAuthorizationError(e: Throwable)

    /**
     * The most general case of an error having occurred while attempting to get remote data. It is
     * probably appropriate to bring up a modal error dialog informing them of this. This method should begin by
     * hiding any progress view now showing.
     */
    fun onError(message: String?)

    /**
     * Invoked instead of onError when the app discovers that no network is available to the device.
     **/
    fun onNoNetworkError()

    /**
     * Display the progress view on the screen. If modal, this will prevent interaction with the app until the
     * progress view is later removed. showProgress() should begin by hiding any error messages now showing.
     */
    fun showProgress(message: String)

}
