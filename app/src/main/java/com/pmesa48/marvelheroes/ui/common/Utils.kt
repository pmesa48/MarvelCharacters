package com.pmesa48.marvelheroes.ui.common

import android.content.Context
import android.util.DisplayMetrics

object Utils {

    fun dpToPx(dp: Int, context: Context) =
        (dp * ( context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

}