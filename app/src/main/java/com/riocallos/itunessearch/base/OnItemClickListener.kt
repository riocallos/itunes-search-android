package com.riocallos.itunessearch.base

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(v: View, item: T, position: Int)
}