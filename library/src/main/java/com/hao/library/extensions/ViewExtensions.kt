package com.hao.library.extensions

import android.graphics.Paint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Yang Shihao
 */

fun <VH : RecyclerView.ViewHolder, A : RecyclerView.Adapter<VH>> RecyclerView.init(adapter: A, layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

fun <VH : RecyclerView.ViewHolder, A : RecyclerView.Adapter<VH>> RecyclerView.init(adapter: A, column: Int) {
    this.layoutManager = GridLayoutManager(context, column)
    this.adapter = adapter
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.visibility(visible: Boolean) {
    if (visible) {
        visible()
    } else {
        gone()
    }
}

/**
 * 中划线
 */
fun TextView.strikeThru(){
    paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
}

/**
 * 下划线
 */
fun TextView.underline(){
    paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

fun View.snack(msg: String?) {
    if (!TextUtils.isEmpty(msg)) {
        Snackbar.make(this, msg!!, Snackbar.LENGTH_SHORT).show()
    }
}

fun EditText.addTextChangedListener(textInputLayout: TextInputLayout) {

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!TextUtils.isEmpty(textInputLayout.error)) {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = ""
                textInputLayout.isErrorEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }
    })
}

fun TextInputLayout.showError(msg: String) {
    isErrorEnabled = true
    error = msg
}

fun TextInputLayout.showError(@StringRes resId: Int) {
    isErrorEnabled = true
    error = context.getString(resId)
}
