package com.learning.learningbydoing.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.learning.learningbydoing.R
import com.learning.learningbydoing.adapter.MaterialsAdapter
import com.learning.learningbydoing.databinding.ActivityMainBinding
import com.learning.learningbydoing.presentation.content.ContentActivity
import com.learning.learningbydoing.presentation.user.UserActivity
import com.learning.learningbydoing.repository.Repository
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var materialsAdapter: MaterialsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Init
        materialsAdapter = MaterialsAdapter()

        getDataMaterial()
        onAction()
    }

    override fun onResume() {
        super.onResume()
        if (intent != null){
            val position = intent.getIntExtra(EXTRA_POSITION, 0)
            mainBinding.rvMaterialsMain.smoothScrollToPosition(position)
        }
    }

    private fun getDataMaterial() {
        showLoading()
        val materials = Repository.getMaterials(this)

        Handler(Looper.getMainLooper())
            .postDelayed({
                materials?.let {
                    materialsAdapter.materials = it
                }
            }, 1200)

        mainBinding.rvMaterialsMain.adapter = materialsAdapter
    }

    private fun showLoading() {
        mainBinding.swipeMain.isRefreshing = true
    }

    private fun hideLoading() {
        mainBinding.swipeMain.isRefreshing = false
    }

    private fun onAction() {
        mainBinding.apply {
            ivAvatarMain.setOnClickListener {
                startActivity<UserActivity>()
            }

            swipeMain.setOnRefreshListener {
                getDataMaterial()
            }
        }

        materialsAdapter.onClick { material, position ->
            startActivity<ContentActivity>(
                    ContentActivity.EXTRA_MATERIAL to material,
                    ContentActivity.EXTRA_POSITION to position
            )
        }
    }
}