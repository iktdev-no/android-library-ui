package no.iktdev.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import no.iktdev.ui.databinding.ViewNestedRecyclerViewBinding

class NestedRecyclerView(context: Context, val attr: AttributeSet? = null) : RecyclerView(context, attr) {

    data class InnerStyles(
        @DrawableRes var background: Int? = null,
        var backgroundTint: Int? = null,
        var headerTextSizeInPixel: Int? = null,
        var headerTextColor: Int? = null,
        var emptyTextColor: Int? = null,
        @DrawableRes var emptyIconSrc: Int? = null,
        var emptyIconSrcTint: Int? = null,
        @DrawableRes var emptyBackground: Int? = null,
        var emptyBackgroundTint: Int? = null
    )

    fun TypedArray.ifPresent(@StyleableRes id: Int, block: (id: Int) -> Unit) {
        if (this.hasValue(id)) {
            block(id)
        }
    }

    fun TypedArray.colorOrDefault(@StyleableRes id: Int): Int {
        return this.getColor(id, 4054148)
    }

    init {
        val innerStyles = InnerStyles()
        val a = context.theme.obtainStyledAttributes(attr, R.styleable.NestedRecyclerView,
            0, 0)
        Styling.applyBackgroundAttr(a, rootView, R.styleable.NestedRecyclerView_android_background)
        Styling.applyBackgroundTintAttr(
            a,
            rootView,
            R.styleable.NestedRecyclerView_android_backgroundTint
        )

        a.ifPresent(R.styleable.NestedRecyclerView_headerTextSize) {
            innerStyles.headerTextSizeInPixel = a.getDimensionPixelSize(it, 72)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_titleTextColor) {
            innerStyles.headerTextColor = a.colorOrDefault(it)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_recyclerBackground) {
            val resourceId = a.getResourceId(it, 0)
            if (resourceId != 0) {
                innerStyles.background = resourceId
            }
        }
        a.ifPresent(R.styleable.NestedRecyclerView_recyclerBackgroundTint) {
            innerStyles.backgroundTint = a.colorOrDefault(it)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_emptyTextColor) {
            innerStyles.emptyTextColor = a.colorOrDefault(it)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_emptySrc) {
            val resourceId = a.getResourceId(it, 0)
            if (resourceId != 0) {
                innerStyles.emptyIconSrc = resourceId
            }
        }
        a.ifPresent(R.styleable.NestedRecyclerView_emptySrcTint) {
            innerStyles.emptyIconSrcTint = a.colorOrDefault(it)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_emptyBackground) {
            innerStyles.emptyBackground = a.colorOrDefault(it)
        }
        a.ifPresent(R.styleable.NestedRecyclerView_emptyBackgroundTint) {
            innerStyles.emptyBackgroundTint = a.colorOrDefault(it)
        }

        a.recycle()

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = NestedAdapter(context, emptyList(), innerStyles)
    }

    class NestedAdapter(val context: Context, items: List<NestedAdapterData<*>>, val attrs: InnerStyles? = null) : Adapter<ViewHolder>() {
        private val _items: MutableList<NestedAdapterData<*>> = items.toMutableList()

        fun add(item: NestedAdapterData<*>) {
            _items.add(item)
            notifyItemInserted(_items.indexOf(item))
        }

        fun add(items: List<NestedAdapterData<*>>) {
            _items.addAll(items)
            notifyItemRangeChanged(_items.indexOf(items.first()), items.size)
        }

        fun remove(item: NestedAdapterData<*>) {
            val index = _items.indexOf(item)
            if (index > -1) {
                _items.removeAt(index)
                notifyItemRemoved(index)
            }
        }

        fun clear() {
            val total = itemCount
            _items.clear()
            notifyItemRangeRemoved(0, total)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestableViewHolder {
            val binding = ViewNestedRecyclerViewBinding.inflate(LayoutInflater.from(context), parent, false).also {
                attrs?.let { a ->
                    a.headerTextSizeInPixel?.let { px -> it.nestedTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, px.toFloat()) }
                    a.headerTextColor?.let { color -> it.nestedTitle.setTextColor(color) }
                    a.emptyIconSrc?.let { drawableRes -> it.emptyImage.setImageResource(drawableRes) }
                    a.emptyIconSrcTint?.let { color -> it.emptyImage.setColorFilter(color) }
                }
            }
            return NestableViewHolder(binding)

        }

        override fun getItemCount(): Int {
            return _items.size
        }

        class NestableViewHolder(private val binding: ViewNestedRecyclerViewBinding) : ViewHolder(binding.root) {
            fun set(context: Context, data: NestedAdapterData<*>) {
                binding.nestedTitle.text = data.title
                binding.nestedRecycler.adapter = data.adapter
                binding.nestedRecycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                /*binding.emptyContainer.visibility = if (data.adapter.itemCount == 0) VISIBLE else GONE
                if (data.adapter.itemCount == 0)
                    setEmpty(context, data)*/
            }
            private fun setEmpty(context: Context, data: NestedAdapterData<*>) {
                if ((data.emptyMinHeight ?: 0) > 0)
                    data.emptyMinHeight?.let { binding.emptyContainer.layoutParams.height = fromDP(context, it) }
                binding.emptyText.text = if (data.emptyText?.isNotEmpty() == true) data.emptyText else if (data.emptyTextRes != 0 && data.emptyTextRes != null) context.getString(data.emptyTextRes) else ""
                if (data.emptyIconRes != 0 && data.emptyIconRes != null)
                    binding.emptyImage.setImageResource(data.emptyIconRes)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = _items[position]
            if (holder is NestableViewHolder)
                holder.set(context, data)
        }
    }
}

abstract class DynamicNestedRecyclerAdapter<T: ViewHolder>(open val listener: Events? = null): RecyclerView.Adapter<T>() {

    interface Events {
        fun <I> onItemClicked(item: I)

    }
}

data class NestedAdapterData<A : RecyclerView.Adapter<*>>(
    val id: String,
    val title: String,
    val adapter: A,
    val emptyMinHeight: Int? = 0,
    @DrawableRes
    val emptyIconRes: Int? = 0,
    @StringRes
    val emptyTextRes: Int? = 0,
    val emptyText: String? = null
)
