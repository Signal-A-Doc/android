/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.signalDoc_patient.R;
import com.signalDoc_patient.model.CategoriesData;
import com.signalDoc_patient.ui.activity.BaseActivity;

import java.util.ArrayList;

public class CategoriesListAdapter extends ArrayAdapter<CategoriesData> {
    private BaseActivity activity;
    private ArrayList<CategoriesData> datas;

    public CategoriesListAdapter(BaseActivity context, ArrayList<CategoriesData> datas) {
        super(context, 0, datas);
        this.activity = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(parent);
        }
        bindView(position, convertView);
        return (convertView);
    }

    private View newView(ViewGroup parent) {
        return (activity.getLayoutInflater().inflate(R.layout.adapter_categories_list, parent, false));
    }

    private void bindView(int position, View convertView) {
        CheckBox checkCB = convertView.findViewById(R.id.checkCB);
        TextView titleTV = convertView.findViewById(R.id.titleTV);

        CategoriesData data = datas.get(position);

        titleTV.setText(data.getTitle());

        if (data.getIscheked()) {
            checkCB.setChecked(true);
        } else {
            checkCB.setChecked(false);
        }

    }

    @Override
    public int getCount() {
        return datas.size();

    }
}