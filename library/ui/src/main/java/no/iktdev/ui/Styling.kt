package no.iktdev.ui

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleableRes

object Styling {
    fun applyTextAttr(attr: TypedArray, text: TextView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            text.text = attr.getString(id)
        }
    }

    fun applyTextColorAttr(attr: TypedArray, text: TextView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val color: Int = attr.getColor(id, 4054148)
            text.setTextColor(color)
        }
    }

    fun applySrcAttr(attr: TypedArray, image: ImageView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            image.setImageResource(attr.getResourceId(id, 0))
        }
    }

    fun applySrcTintAttr(attr: TypedArray, image: ImageView, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val color: Int = attr.getColor(id, 4054148)
            image.setColorFilter(color)
        }
    }

    fun applyBackgroundTintAttr(attr: TypedArray, view: View, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val color: Int = attr.getColor(id, 4054148)
            view.backgroundTintList = ColorStateList.valueOf(color)
        }
    }

    fun getBooleanAttrValue(attr: TypedArray, @StyleableRes id: Int): Boolean {
        return if (attr.hasValue(id)) attr.getBoolean(id, false) else false
    }

    fun getIntAttrValue(attr: TypedArray, @StyleableRes id: Int, default: Int = 0): Int {
        return if (attr.hasValue(id)) attr.getInt(id, default) else default
    }

    fun applyBackgroundAttr(attr: TypedArray, view: View, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            val drawable: Drawable? = attr.getDrawable(id)
            if (drawable == null) {
                // TODO: Inform that we have a problem
                return
            }
            view.background = drawable
        }
    }
}