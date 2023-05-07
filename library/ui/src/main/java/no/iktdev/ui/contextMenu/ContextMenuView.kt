package no.iktdev.ui.contextMenu

import android.view.ContextMenu
import android.view.View
import no.iktdev.ui.R

class ContextMenuView(val title: String, private val actions: List<ContextMenuViewItem>): View.OnCreateContextMenuListener {
    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0?.setHeaderTitle(title)
        actions.forEach {
            val menuItem = p0?.add(it.title)
            menuItem?.setOnMenuItemClickListener(it.click)
            if (it.icon != null)
                menuItem?.setIcon(it.icon)
        }
    }

}