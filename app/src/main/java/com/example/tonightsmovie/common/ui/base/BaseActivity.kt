package com.example.tonightsmovie.common.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tonightsmovie.utils.ext.collect
import kotlinx.coroutines.flow.Flow

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    open fun setupObservers() {
        /*
            The class that will extend BaseActivity can override this function
            and there is no specific or common implementation to be placed here
         */
    }

    //Should be called in onCreate to observe an event
    protected fun <E> observeEvent(events: Flow<E>, actions: (it: E) -> Unit) {
        collect(
            flow = events,
            action = actions::invoke
        )
    }

}