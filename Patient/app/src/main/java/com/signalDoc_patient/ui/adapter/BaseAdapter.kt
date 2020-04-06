/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var pageEndListener: PageEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(null)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 0
    }

    interface OnItemClickListener {
        fun onItemClick(vararg itemData: Any)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    internal fun onItemClick(vararg itemData: Any) {
        if (onItemClickListener != null) {
            onItemClickListener!!.onItemClick(*itemData)
        }
    }

    interface PageEndListener {
        fun onPageEnd()
    }

    fun setOnPageEndListener(pageEndListener: PageEndListener) {
        this.pageEndListener = pageEndListener
    }

    internal fun onPageEnd() {
        if (pageEndListener != null) {
            pageEndListener!!.onPageEnd()
        }
    }

    fun emptyNullCheck(s: String?, defaultS: String): String {
        var defaultS = defaultS
        if (s != null && !s.isEmpty()) {
            defaultS = s
        }
        return defaultS
    }
}