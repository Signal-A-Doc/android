/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.utils;

import com.signalDoc_doctor.model.CategoriesData;

import java.util.ArrayList;

public class SpecilizationEditProfile {

    private static SpecilizationEditProfile editProfile = new SpecilizationEditProfile();
    private CallBack callBack;

    public static SpecilizationEditProfile getInstance() {
        return editProfile;
    }

    public void setListener(CallBack callBack) {
        this.callBack = callBack;
    }

    public void sendCallBack(ArrayList<CategoriesData> list) {
        callBack.whatSpecilizationData(list);
    }


    public interface CallBack {
        void whatSpecilizationData(ArrayList<CategoriesData> list);
    }

}
