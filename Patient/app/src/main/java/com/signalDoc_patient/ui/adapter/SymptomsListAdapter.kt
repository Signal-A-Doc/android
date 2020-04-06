package com.signalDoc_patient.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CompoundButton
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import com.signalDoc_patient.R
import com.signalDoc_patient.model.SymptomsListData
import com.signalDoc_patient.ui.activity.BaseActivity
import com.signalDoc_patient.ui.fragment.patient.AppointmentInfoThreeFragment

class SymptomsListAdapter(var baseActivity: BaseActivity, var datas: ArrayList<SymptomsListData>, var fragment: AppointmentInfoThreeFragment) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any {
        return groupPosition
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val mExpandableListView: ExpandableListView = parent as ExpandableListView
        mExpandableListView.expandGroup(groupPosition)

        if (convertView == null) {
            convertView = LayoutInflater.from(baseActivity).inflate(R.layout.adapter_parent_symptoms, parent, false)
        }
        val titleTV = convertView!!.findViewById<TextView>(R.id.titleTV)
        if (datas.size > 0) {
            val parentData = datas.get(groupPosition)
            titleTV.text = parentData.title
        }


        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return datas.get(groupPosition).symptoms!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childPosition
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, viewGroup: ViewGroup?): View {
        var convertView = convertView


        if (convertView == null) {
            convertView = LayoutInflater.from(baseActivity).inflate(R.layout.adapter_child_symptoms, viewGroup, false)
        }

        val subTitleTV = convertView!!.findViewById<AppCompatTextView>(R.id.subTitleTV)
        val subTitleCB = convertView.findViewById<AppCompatCheckBox>(R.id.subTitleCB)
        if (datas.isNotEmpty() && datas[groupPosition].symptoms!!.isNotEmpty()) {
            val subMenuData = datas.get(groupPosition).symptoms!!.get(childPosition)
            subTitleCB.isChecked = subMenuData.is_checked
            subTitleTV.text = subMenuData.title
            subTitleCB.setOnCheckedChangeListener { buttonView, isChecked ->
                subMenuData.is_checked = isChecked

            }
        }



        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return datas.size
    }
}