package com.riocallos.itunessearch.features.search.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Lifecycle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.riocallos.itunessearch.R
import com.riocallos.itunessearch.base.BaseViewModelActivity
import com.riocallos.itunessearch.databinding.ActivitySearchResultBinding
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.features.search.SearchResultState
import com.riocallos.itunessearch.features.search.SearchResultViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class SearchResultActivity : BaseViewModelActivity<ActivitySearchResultBinding, SearchResultViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_search_result

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupVmObservers()
        setupVm()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onPause() {
        super.onPause()
        player?.let {
            it.release()
        }
    }

    override fun onBackPressed() {
        viewModel.removeSearchResult()
        super.onBackPressed()
    }

    private fun setupViews() {

        showLoadingDialog()

        binding.searchResult = SearchResult()

        player = SimpleExoPlayer.Builder(this).build()
        player?.let {
            it.apply {
                playWhenReady = true
            }
        }

        binding.playerView.player = player

        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            null,
            DefaultHttpDataSourceFactory(
                Util.getUserAgent(
                    this,
                    getString(R.string.app_name)
                ), null
            )
        )

        binding.play.setOnClickListener {

            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                Uri.parse(viewModel.searchResult.preview)
            )

            player?.let {
                it.stop(true)
                it.prepare(videoSource)
                binding.playerView.visibility = View.VISIBLE
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

    private fun handleState(state: SearchResultState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            SearchResultState.ShowProgressLoading -> {
                showLoadingDialog()
            }
            SearchResultState.HideProgressLoading -> {
                dismissDialogs()
            }
            is SearchResultState.ShowSearchResult -> {
                binding.searchResult = state.searchResult
            }
        }
    }

    private fun setupVm() {
        val bundle = intent.extras
        viewModel.localSearchResult(bundle?.getString("search_result")!!)
    }

}