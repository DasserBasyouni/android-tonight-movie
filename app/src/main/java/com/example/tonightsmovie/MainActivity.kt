package com.example.tonightsmovie

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.example.tonightsmovie.databinding.ActivityMainBinding
import com.example.tonightsmovie.common.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

}

