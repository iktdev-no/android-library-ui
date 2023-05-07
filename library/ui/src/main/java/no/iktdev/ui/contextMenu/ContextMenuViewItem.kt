package no.iktdev.ui.contextMenu

import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes

data class ContextMenuViewItem(
    @DrawableRes val icon: Int? = null,
    val title: String,
    val click: MenuItem.OnMenuItemClickListener
)
