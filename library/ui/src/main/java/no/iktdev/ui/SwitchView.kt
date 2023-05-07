package no.iktdev.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import no.iktdev.ui.databinding.ViewSwitchBinding

class SwitchView(context: Context, attrs: AttributeSet? = null) : ComponentBase(context, attrs) {
    override var binding: ViewSwitchBinding = ViewSwitchBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.viewSettingSwitchText, R.styleable.SwitchView_android_text)
        applyTextAttr(a, binding.viewSettingSwitchSubText, R.styleable.SwitchView_subText)

        applyTextColorAttr(a, binding.viewSettingSwitchText, R.styleable.SwitchView_android_textColor)
        applyTextColorAttr(a, binding.viewSettingSwitchSubText, R.styleable.SwitchView_subTextColor)

        applySrcAttr(a, binding.viewSettingSwitchImage, R.styleable.SwitchView_android_src)
        applySrcTintAttr(a, binding.viewSettingSwitchImage, R.styleable.SwitchView_srcTint)

        applyBackgroundAttr(a, binding.root, R.styleable.SwitchView_android_background)
        applyBackgroundTintAttr(a, binding.root, R.styleable.SwitchView_android_backgroundTint)

        binding.viewSettingSwitch.isChecked = getBooleanAttrValue(a, R.styleable.SwitchView_android_checked)
        if (binding.viewSettingSwitchSubText.text.isNullOrEmpty())
            binding.viewSettingSwitchSubText.visibility = GONE
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SwitchView)
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