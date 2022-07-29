package com.learning.learningbydoing.presentation.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager.widget.ViewPager
import com.learning.learningbydoing.R
import com.learning.learningbydoing.adapter.PagesAdapter
import com.learning.learningbydoing.databinding.ActivityContentBinding
import com.learning.learningbydoing.model.Material
import com.learning.learningbydoing.model.Page
import com.learning.learningbydoing.presentation.main.MainActivity
import com.learning.learningbydoing.repository.Repository
import com.learning.learningbydoing.utils.disabled
import com.learning.learningbydoing.utils.enabled
import com.learning.learningbydoing.utils.invisible
import com.learning.learningbydoing.utils.visible
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ContentActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MATERIAL = "extra_material"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var contentBinding: ActivityContentBinding
    private lateinit var pagesAdapter: PagesAdapter
    private var currentPosition = 0
    private var materialPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(contentBinding.root)

        // Init
        pagesAdapter = PagesAdapter(this)

        getDataIntent()
        onAction()
        viewPageCurrentPosition()
    }

    private fun viewPageCurrentPosition() {
        contentBinding.vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val totalIndex = pagesAdapter.count
                currentPosition = position
                val textIndex = "${currentPosition + 1} / $totalIndex"
                contentBinding.tvIndexContent.text = textIndex


            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun getDataIntent() {
        if (intent != null){
            materialPosition = intent.getIntExtra(EXTRA_POSITION, 0)
            val material = intent.getParcelableExtra<Material>(EXTRA_MATERIAL)

            contentBinding.tvTitleContent.text = material?.titleMaterial

            material?.let { getDataContent(material) }
        }
    }

    private fun getDataContent(material: Material) {
        showLoading()
        val content = material.idMaterial?.let { Repository.getContents(this)?.get(it) }

        Handler(Looper.getMainLooper())
                .postDelayed({
                    hideLoading()

                    pagesAdapter.pages = content?.pages as MutableList<Page>
                    contentBinding.vpContent.adapter = pagesAdapter
                    contentBinding.vpContent.setPagingEnabled(false)

                    // Init untuk tampilan awal index
                    val textIndex = "${currentPosition + 1} / ${pagesAdapter.count}"
                    contentBinding.tvIndexContent.text = textIndex

                    if (currentPosition == 0){
                        contentBinding.btnPrevContent.invisible()
                        contentBinding.btnPrevContent.disabled()
                    } else {
                        contentBinding.btnPrevContent.visible()
                        contentBinding.btnPrevContent.enabled()
                    }
                }, 1200)
    }

    private fun showLoading() {
        contentBinding.swipeContent.isRefreshing = true
    }

    private fun hideLoading() {
        contentBinding.swipeContent.isRefreshing = false
    }


    private fun onAction() {
        contentBinding.apply {
            btnCloseContent.setOnClickListener { finish() }

            btnNextContent.setOnClickListener {
                if (currentPosition < pagesAdapter.count - 1){
                    contentBinding.vpContent.currentItem += 1
                } else {
                    startActivity<MainActivity>(
                            MainActivity.EXTRA_POSITION to materialPosition + 1
                    )
                    finish()
                }
            }

            btnPrevContent.setOnClickListener {
                contentBinding.vpContent.currentItem -= 1
            }

            swipeContent.setOnRefreshListener {
                getDataIntent()
            }
        }
    }
}