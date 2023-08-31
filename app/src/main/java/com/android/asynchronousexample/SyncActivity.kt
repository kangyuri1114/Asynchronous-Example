package com.android.asynchronousexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SyncActivity : AppCompatActivity() {

    private lateinit var apiFragment: ApiFragment
    private lateinit var progressBarFragment: ProgressBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        apiFragment = ApiFragment()
        progressBarFragment = ProgressBarFragment()

        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(R.id.fragment_container_1, apiFragment)
        transaction.add(R.id.fragment_container_2, progressBarFragment)

        transaction.commit()

        // 코루틴을 사용하여 비동기적으로 프로그레스 바 처리
        GlobalScope.launch(Dispatchers.Main) {
            while (isActive) {
                progressBarFragment.updateSeekBar()
                kotlinx.coroutines.delay(1000) // 1초마다 업데이트
            }
        }
    }
}
