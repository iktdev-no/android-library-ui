package no.iktdev.ui

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.StyleableRes
import no.iktdev.ui.databinding.ComponentTitledTextInputBinding

class TitledTextInput(context: Context, attrs: AttributeSet? = null) : ComponentBase(context, attrs) {
    override val binding: ComponentTitledTextInputBinding = ComponentTitledTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) this.applyAttrs(attrs)

        binding.input.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                onTextChanged?.onChanged(p0.toString())

            }
        })
    }


    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitledTextInput)
        onTypedArray(a)
        a.recycle()
    }



    override fun onTypedArray(a: TypedArray) {
        applyHintAttr(a, binding.input, R.styleable.TitledTextInput_android_hint)

        applyTextAttr(a, binding.title, R.styleable.TitledTextInput_title)


        applyTextColorAttr(a, binding.title, R.styleable.TitledTextInput_titleTextColor)
        applyBackgroundTintAttr(a, binding.root, R.styleable.TitledTextInput_android_backgroundTint)

        applyBackgroundAttr(a, binding.input, R.styleable.TitledTextInput_border)
        applyBackgroundAttr(a, binding.root, R.styleable.TitledTextInput_android_background)

        applyHintAttr(a, binding.input, R.styleable.TitledTextInput_android_hint)
        applyAutofillHints(a, binding.input, R.styleable.TitledTextInput_android_autofillHints)
        applyInputType(a, binding.input, R.styleable.TitledTextInput_android_inputType)
    }

    fun applyHintAttr(attr: TypedArray, text: EditText, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            text.hint = attr.getString(id)
        }
    }

    fun applyAutofillHints(attr: TypedArray, text: EditText, @StyleableRes id: Int) {
        if (attr.hasValue(id) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text.setAutofillHints(attr.getString(id))
        }
    }

    fun applyInputType(attr: TypedArray, text: EditText, @StyleableRes id: Int) {
        if (attr.hasValue(id)) {
            text.inputType = attr.getInt(id, 0)
        }
    }

    fun setText(text: String) {
        binding.input.setText(text)
    }

    fun getText(): String {
        return binding.input.text.toString()
    }

    interface OnTextChanged {
        fun onChanged(text: String)
    }

    private var onTextChanged: OnTextChanged? = null
    fun setOnTextChanged(changedListener: OnTextChanged) {
        onTextChanged = changedListener
    }


}