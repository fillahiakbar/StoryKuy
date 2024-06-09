package com.example.fillstoryapp.view.custome

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.fillstoryapp.R
import com.google.android.material.textfield.TextInputEditText

class CustomePass @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs)   {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    setError(context.getString( R.string.password_format), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?)  {

            }
        })
    }
}