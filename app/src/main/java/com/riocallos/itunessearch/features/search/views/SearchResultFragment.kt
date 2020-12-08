package com.riocallos.itunessearch.features.search.views

import android.net.Uri
import android.os.Bundle
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
import com.riocallos.itunessearch.base.BaseViewModelFragment
import com.riocallos.itunessearch.databinding.FragmentSearchResultBinding
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.features.main.MainActivity
import com.riocallos.itunessearch.features.search.SearchResultState
import com.riocallos.itunessearch.features.search.SearchResultViewModel
import com.riocallos.itunessearch.utils.AppLogger
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

open class SearchResultFragment : BaseViewModelFragment<FragmentSearchResultBinding, SearchResultViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_search_result

    private lateinit var player: ExoPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupVmObservers()
        setupVm()
    }

    override fun onPause() {
        super.onPause()
        player.release()
    }

    private fun setupViews() {
        (activity as MainActivity).showLoadingDialog()
        binding.searchResult = SearchResult()

        player = SimpleExoPlayer.Builder(activity as MainActivity).build()
        player.playWhenReady = true
        binding.playerView.player = player

        binding.play.setOnClickListener {

            val dataSourceFactory = DefaultDataSourceFactory(
                activity,
                null,
                DefaultHttpDataSourceFactory(
                    Util.getUserAgent(
                        activity as MainActivity,
                        getString(R.string.app_name)
                    ), null
                )
            )

            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                Uri.parse(viewModel.searchResult.preview))

            player.prepare(videoSource)

            binding.playerView.visibility = View.VISIBLE

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
                (activity as MainActivity).showLoadingDialog()
            }
            SearchResultState.HideProgressLoading -> {
                (activity as MainActivity).dismissDialogs()
            }
            is SearchResultState.ShowSearchResult -> {
                binding.searchResult = state.searchResult
            }
        }
    }

    private fun setupVm() {
        AppLogger.longError("arguments?.getString(\"search_result\")!! " + arguments?.getString("search_result")!!)
        viewModel.localSearchResult(arguments?.getString("search_result")!!)
    }


}