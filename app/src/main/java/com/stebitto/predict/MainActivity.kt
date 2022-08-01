package com.stebitto.predict

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.stebitto.agify.api.IAgifyRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import timber.log.Timber
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
                .catch { e ->
                    textView.text = e.message
                }
                .collect {
                    textView.text = it.toString()
                }
        }
    }
}