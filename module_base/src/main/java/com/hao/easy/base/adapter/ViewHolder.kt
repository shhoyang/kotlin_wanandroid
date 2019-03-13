package com.hao.easy.base.adapter

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.load
import com.hao.easy.base.extensions.visible

class ViewHolder(val context: Context, parent: ViewGroup, @LayoutRes layoutId: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false)) {

    private val views = SparseArray<View>()

    fun <T : View> getView(viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            if (view == null) {
                throw IllegalArgumentException("not found id")
            }
            views.put(viewId, view)
        }

        return view as T
    }

    fun setText(@IdRes viewId: Int, text: CharSequence): ViewHolder {
        val textView: TextView = getView(viewId)
        textView.text = text
        return this
    }

    fun setText(@IdRes viewId: Int, @StringRes resId: Int): ViewHolder {
        val textView: TextView = getView(viewId)
        textView.text = context.getString(resId)
        return this
    }

    fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): ViewHolder {
        val imageView: ImageView = getView(viewId)
        imageView.setImageResource(resId)
        return this
    }

    fun setImage(@IdRes viewId: Int, url: String): ViewHolder {
        val imageView: ImageView = getView(viewId)
        imageView.load(url)
        return this
    }

    fun gone(@IdRes viewId: Int): ViewHolder {
        val view: View = getView(viewId)
        view.gone()
        return this
    }

    fun gone(view: View): ViewHolder {
        view.gone()
        return this
    }

    fun visible(@IdRes viewId: Int): ViewHolder {
        val view: View = getView(viewId)
        view.visible()
        return this
    }

    fun visible(view: View): ViewHolder {
        view.visible()
        return this
    }

    fun setClickListener(@IdRes viewId: Int, f: (View) -> Unit): ViewHolder {
        getView<View>(viewId).apply {
            setOnClickListener { f(this) }
        }
        return this
    }

    fun setClickListener(viewIds: Array<Int>, f: (View) -> Unit): ViewHolder {
        viewIds.forEach {
            getView<View>(it).apply {
                setOnClickListener { f(this) }
            }
        }
        return this
    }

    fun setLongClickListener(@IdRes viewId: Int, f: (View) -> Boolean): ViewHolder {
        getView<View>(viewId).apply {
            setOnLongClickListener {
                f(this)
            }
        }
        return this
    }
}
