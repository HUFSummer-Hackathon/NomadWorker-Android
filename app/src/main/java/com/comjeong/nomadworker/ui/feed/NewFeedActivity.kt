package com.comjeong.nomadworker.ui.feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.comjeong.nomadworker.databinding.ActivityNewFeedBinding

class NewFeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}