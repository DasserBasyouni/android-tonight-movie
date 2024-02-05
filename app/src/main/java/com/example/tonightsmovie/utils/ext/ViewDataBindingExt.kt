package com.example.tonightsmovie.utils.ext

import androidx.databinding.ViewDataBinding

/**
 *
 */


fun <T : ViewDataBinding> T.executeWithAction(action: T.() -> Unit) {
    action()
    executePendingBindings()
}
