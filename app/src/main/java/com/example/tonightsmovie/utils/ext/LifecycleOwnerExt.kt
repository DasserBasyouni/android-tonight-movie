package com.example.tonightsmovie.utils.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * [collect] will collect every value,
 * while [collectLatest] will stop current work to collect latest value,
 *
 * The crucial difference from [collect] is that when the original flow emits a new value,
 * then the action block for the previous value is cancelled.
 *
 * @link: https://stackoverflow.com/a/70603576/5873832
 *
 *
 * "Never collect a flow from the UI directly from launch or the launchIn extension function
 * if the UI needs to be updated. These functions process events even when the view is not visible.
 * This behavior can lead to app crashes. To avoid that, use the repeatOnLifecycle API as shown above."
 *
 * @link: https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
 */

fun <T> LifecycleOwner.collectLatest(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}

fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                action.invoke(it)
            }
        }
    }
}