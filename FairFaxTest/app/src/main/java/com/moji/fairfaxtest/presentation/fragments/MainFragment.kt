package com.moji.fairfaxtest.presentation.fragments

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.moji.fairfaxtest.R
import com.moji.fairfaxtest.domain.entities.NewsAssetView
import com.moji.fairfaxtest.presentation.Listeners.NewsListener
import com.moji.fairfaxtest.presentation.Listeners.NewsOnClickListener
import com.moji.fairfaxtest.presentation.presenters.NewsPresenter
import com.moji.fairfaxtest.presentation.recyclerviews.NewsAdapter
import kotlinx.android.synthetic.main.fragment_main.*



/**
 * Created by moji on 7/3/18.
 */
// NewsListener: used to listen to presenters call backs
// NewsOnClickListener: used to listen to recyclerView callbacks
class MainFragment :  ToolbarFragment() , NewsListener, NewsOnClickListener {
    companion object {
        // used to pass extras to another activity through intents
        const val EXTRA_URL : String = "extra_url"
        const val LIST_STATE_KEY = "recycler_list_state"
    }
    // NewsPresenter: used to call APIs and separate presentation layer from data layer
    private lateinit var presenter : NewsPresenter
    private val newsAdapter = NewsAdapter(emptyList(), this)
    private var listState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter = NewsPresenter(activity, this)
        // asking presenter to retrieve news list from the server
        // the result would be received through NewsListener callbacks
        presenter.getNewsList()

        setViews()
    }

    private fun setViews(){
        swipeRefreshNews.setOnRefreshListener{
            // whenever user pull the recyclerView down a new for news list will be sent
            // and the UI will be refreshed
            presenter.getNewsList()
        }

        // set toolbar title
        setToolbarTitle(getString(R.string.main_fragment_title))

        // setting up RecyclerView and assigning its adapter to it
        recyclerNews.setHasFixedSize(true)
        recyclerNews.layoutManager = LinearLayoutManager(activity)
        recyclerNews.adapter = newsAdapter
    }

    override fun onSaveInstanceState(state: Bundle?) {
        super.onSaveInstanceState(state)
        // Save list state
        listState = recyclerNews.layoutManager.onSaveInstanceState()
        state?.putParcelable(LIST_STATE_KEY, listState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
    }

    // this is called when requesting for news list is successful
    override fun onNewsFetched(newsAssets: List<NewsAssetView>) {
        newsAdapter.data = newsAssets
        // check if there is a saved list state that has not been applied
        if (listState != null) {
            recyclerNews.layoutManager.onRestoreInstanceState(listState)
            listState = null
        }
    }

    // this is called when users tap on "Read More>>" link
    override fun onNewsAssetClick(url: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = ArticleFragment()
        val bundle = Bundle()
        bundle.putString(EXTRA_URL, url)   //parameters are (key, value).
        fragment.arguments = bundle
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left
                , R.animator.slide_in_left, R.animator.slide_out_left)
        fragmentTransaction.add(R.id.fragment_place_holder, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }
    // this is called when presenter is done with api calling
    override fun hideProgress() {
        swipeRefreshNews.isRefreshing = false
    }

    // this is called when 401 error is sent from server
    // in certain circumstances this should navigate user to login page
    override fun onAuthorizationError(e: Throwable) {
        Toast.makeText(activity,getString(R.string.error_authentication_failed), Toast.LENGTH_LONG).show()
    }

    // this is called when any error except for 401 and no network error happens
    // during the api call
    override fun onError(message: String?) {
        Toast.makeText(activity,message?:getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
    }

    // this is called when there is no network during the api call
    override fun onNoNetworkError() {
        Toast.makeText(activity,getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
    }

    // this is called when the presenter starts sending request for data
    override fun showProgress(message: String) {
        swipeRefreshNews.isRefreshing = true
    }
}
