package com.stebitto.predict

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.commonexception.HttpException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var agifyRepository: IAgifyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val textView = findViewById<TextView>(R.id.text_log)

        lifecycleScope.launchWhenStarted {
            agifyRepository.getAgePredictionForName("michael")
                .collect { result ->
                    result.onSuccess { textView.text = it.toString() }
                    result.onFailure {
                        with(it as HttpException) {
                            textView.text = "Code: $code | $text"
                        }
                    }
                }
        }
    }
}