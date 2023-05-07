package no.iktdev.ui

import android.content.Context
import android.content.res.Resources
import kotlin.math.roundToInt

fun fromDP(context: Context, dp: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).roundToInt()
}

fun toPixel(context: Context, dp: Int) = fromDP(context, dp)

fun rendererWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun renderHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

/*fun drawableDisplayWidth(context: Context) {

    if(Build.VERSION.SDK_INT >= 30) {
        val defaultDisplay = context.getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
        return defaultDisplay.getRealSize()
    } else {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }



}*/