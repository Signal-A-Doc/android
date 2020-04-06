/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.utils;

public class DataAndTypeEditProfile {

    private static DataAndTypeEditProfile editProfile = new DataAndTypeEditProfile();
    private CallBack callBack;

    public static DataAndTypeEditProfile getInstance() {
        return editProfile;
    }

    public void setListener(CallBack callBack) {
        this.callBack = callBack;
    }

    public void sendCallBack(String data, int type) {
        callBack.whatData(data, type);
    }



    public interface CallBack {
        void whatData(String data, int type);
    }

}
