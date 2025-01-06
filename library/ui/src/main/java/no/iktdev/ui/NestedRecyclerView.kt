package no.iktdev.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import no.iktdev.ui.databinding.ViewNestedRecyclerViewBinding

class NestedRecyclerView(context: Context, attr: AttributeSet) : RecyclerView(context, attr) {

    init {

        val a = context.obtainStyledAttributes(attr, R.styleable.NestedRecyclerView)
        Styling.applyBackgroundAttr(a, rootView, R.styleable.NestedRecyclerView_android_background)
        Styling.applyBackgroundTintAttr(
            a,
            rootView,
            R.styleable.NestedRecyclerView_android_backgroundTint
        )
        a.recycle()

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = NestedAdapter(context, emptyList(), attr)
    }

    class NestedAdapter(val context: Context, items: List<NestedAdapterData<*>>, val attr: AttributeSet? = null) : Adapter<ViewHolder>() {
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
            val a = context.obtainStyledAttributes(attr, R.styleable.NestedRecyclerView)
            val binding =
                ViewNestedRecyclerViewBinding.inflate(LayoutInflater.from(context), parent, false)
            onTypedArray(binding, a)
            a.recycle()
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

        private fun onTypedArray(binding: ViewNestedRecyclerViewBinding, a: TypedArray) {
            Styling.applyBackgroundAttr(
                a,
                binding.nestedRecycler,
                R.styleable.NestedRecyclerView_recyclerBackground
            )
            Styling.applyBackgroundTintAttr(
                a,
                binding.nestedRecycler,
                R.styleable.NestedRecyclerView_recyclerBackgroundTint
            )

            Styling.applyTextColorAttr(
                a,
                binding.nestedTitle,
                R.styleable.NestedRecyclerView_titleTextColor
            )
            Styling.applyTextColorAttr(a, binding.emptyText, R.styleable.NestedRecyclerView_emptyTextColor)
            Styling.applySrcAttr(a, binding.emptyImage, R.styleable.NestedRecyclerView_emptySrc)
            Styling.applySrcTintAttr(a, binding.emptyImage, R.styleable.NestedRecyclerView_emptySrcTint)
            Styling.applyBackgroundAttr(
                a,
                binding.emptyContainer,
                R.styleable.NestedRecyclerView_emptyBackground
            )
            Styling.applyBackgroundTintAttr(
                a,
                binding.emptyContainer,
                R.styleable.NestedRecyclerView_emptyBackgroundTint
            )
            Styling.applyTextSize(
                a,
                binding.nestedTitle,
                R.styleable.NestedRecyclerView_headerTextSize
            )
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
