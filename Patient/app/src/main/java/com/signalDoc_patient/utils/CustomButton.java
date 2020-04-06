

/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton {
    public CustomButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            setFont();
        }
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setFont();
        }
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            setFont();
        }
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/rubik_regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}
