package com.android.asynchronousexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgressBarFragment : Fragment() {

    private lateinit var seekBar: SeekBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekBar = view.findViewById(R.id.seekBar)
    }

    suspend fun updateSeekBar() {
        withContext(Dispatchers.Main) {
            val currentProgress = seekBar.progress
            Log.d("SeekBar 이동 위치", "SeekBar ($currentProgress / 100) 이동")

            if (currentProgress < seekBar.max) {
                seekBar.progress = currentProgress + 10
            } else {
                seekBar.progress = 0
            }
        }
    }
}
