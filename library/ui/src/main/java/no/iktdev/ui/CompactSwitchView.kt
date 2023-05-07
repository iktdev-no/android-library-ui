package no.iktdev.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import no.iktdev.ui.databinding.ViewSwitchBinding
import no.iktdev.ui.databinding.ViewSwitchCompactBinding

class CompactSwitchView (context: Context, attrs: AttributeSet? = null) : ComponentBase(context, attrs) {
    override var binding: ViewSwitchCompactBinding = ViewSwitchCompactBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.viewSettingSwitchText, R.styleable.CompactSwitchView_android_text)

        applyTextColorAttr(a, binding.viewSettingSwitchText, R.styleable.CompactSwitchView_compactSwitchViewTextColor)

        applyBackgroundAttr(a, binding.root, R.styleable.CompactSwitchView_compactSwitchViewBackground)
        applyBackgroundTintAttr(a, binding.root, R.styleable.CompactSwitchView_compactSwitchViewBackgroundTint)

        binding.viewSettingSwitch.isChecked = getBooleanAttrValue(a, R.styleable.CompactSwitchView_android_checked)
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CompactSwitchView)
        onTypedArray(a)
        a.recycle()
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        binding.viewSettingSwitch.setOnCheckedChangeListener(listener)
    }

    init {
        binding.root.setOnClickListener { binding.viewSettingSwitch.toggle() }
        if (attrs != null) this.applyAttrs(attrs)
    }

    fun setChecked(checked: Boolean) {
        binding.viewSettingSwitch.isChecked = checked
    }

    fun isChecked(): Boolean {
        return binding.viewSettingSwitch.isChecked
    }

}