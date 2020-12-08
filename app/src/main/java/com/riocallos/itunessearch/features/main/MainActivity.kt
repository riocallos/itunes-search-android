package com.riocallos.itunessearch.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riocallos.itunessearch.R
import com.riocallos.itunessearch.base.BaseViewModelActivity
import com.riocallos.itunessearch.base.OnItemClickListener
import com.riocallos.itunessearch.databinding.ActivityMainBinding
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.features.search.views.SearchResultActivity
import com.riocallos.itunessearch.features.search.views.SearchResultFragment
import com.riocallos.itunessearch.utils.AnimUtil
import com.riocallos.itunessearch.utils.AppLogger
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>(), OnItemClickListener<SearchResult> {

    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val mainAdapter by lazy {
        MainAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupVmObservers()
        setupVm()
    }

    private fun setupViews() {

        //showLoadingDialog()
        binding.swipeRefreshLayout.isRefreshing = true

        twoPane = binding.frameLayout != null

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.remoteSearch("star", "au", "movie")
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView
            .apply {
                    this.layoutManager = layoutManager
                    post {
                        this.adapter = mainAdapter
                        mainAdapter.itemClickListener = this@MainActivity
                    }
                }

    }

    private fun setupVmObservers() {
        viewModel.state
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(schedulers.ui())
            .subscribeBy(
                onNext = { state ->
                    handleState(state)
                },
                onError = {
                    //AppLogger.error(it.message)
                }
            ).apply {
                disposables.add(this)
            }

    }

    private fun handleState(state: MainState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            MainState.ShowProgressLoading -> {
                binding.swipeRefreshLayout.isRefreshing = true
                //showLoadingDialog()
            }
            MainState.HideProgressLoading -> {
                binding.swipeRefreshLayout.isRefreshing = false
                dismissDialogs()
            }
            is MainState.ShowRetrieved -> {
                binding.retrieved.text = state.retrieved
            }
            is MainState.ShowSearchResults -> {
                binding.swipeRefreshLayout.isRefreshing = false
                mainAdapter.submitList(state.searchResults)
            }
            is MainState.ShowSearchResult -> {
                showSearchResult(state.searchResult)
            }
        }
    }

    private fun setupVm() {
        viewModel.getSearchResult()
        viewModel.getRetrieved()
        viewModel.localSearch()
        viewModel.remoteSearch("star", "au", "movie")
    }

    override fun onItemClick(v: View, item: SearchResult, position: Int) {
        showSearchResult(item.id)
    }

    private fun showSearchResult(id: String) {
        if (twoPane) {
            val fragment = SearchResultFragment()
            val arguments = Bundle()
            arguments.putString("search_result", id)
            fragment.arguments = arguments
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
        } else {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra("search_result", id)
            startActivity(intent)
            overridePendingTransition(AnimUtil.inF(), AnimUtil.inS())
        }
    }

}