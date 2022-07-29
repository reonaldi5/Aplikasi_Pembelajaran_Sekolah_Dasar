package com.learning.learningbydoing.utils

import android.view.View

fun View.visible(){ visibility = View.VISIBLE}
fun View.invisible(){ visibility = View.INVISIBLE}
fun View.disabled(){ isEnabled = false }
fun View.enabled(){ isEnabled = true }