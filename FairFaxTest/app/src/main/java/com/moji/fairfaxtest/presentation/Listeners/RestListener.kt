package com.moji.fairfaxtest.presentation.Listeners

/**
 * Created by moji on 20/2/18.
 */

interface RestListener {
    /**
     * Remove the progress view from the screen. If modal, this will allow interaction with the app to resume. This
     * method should at least hide any error messages now showing.
     */
    fun hideProgress()

    /**
     * Invoked instead of onError when the user turns out not to have permission to do the operation they were
     * attempting. A simple implementation would delegate this to <pre>onError(e.getMessage())</pre>. This method
     * should at least call hideProgress().
     *
     * @param e The cause of the error. Enables implementors to tailor their response.
     */
    fun onAuthorizationError(e: Throwable)

    /**
     * The most general case of an error having occurred while attempting to get remote data. It is
     * probably appropriate to bring up a modal error dialog informing them of this. This method should begin by
     * hiding any progress view now showing.
     *
     * @param message Human readable message that tells the user the nature of the error and, ideally, what they can
     * do to fix it.
     */
    fun onError(message: String?)

    /**
     * Invoked instead of onError when the app discovers that no network is avaiable to the device. A simple
     * implementation would delegate this to <pre>onError("Network unavailable")</pre>. This method should begin by
     * hiding any progress view now showing.
     */
    fun onNoNetworkError()

    /**
     * Display the progress view on the screen. If modal, this will prevent interaction with the app until the
     * progress view is later removed. showProgress() should begin by hiding any error messages now showing.
     *
     * @param message Human readable message to put in the progress view - identifies the operation in progress.
     */
    fun showProgress(message: String)

}
