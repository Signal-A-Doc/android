/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.utils

object Const {

    val REMOVE_TIME = 8596
    val OPEN_PAYMENT = 8585
    val EXPIRY_DATE: Int = 7456
    val ISSUE_DATE: Int = 8546

    //    val SERVER_REMOTE_URL = "http://192.168.2.175/signal-a-doc-yii2-1347/"
    val SERVER_REMOTE_URL = "http://beta.toxsl.in/signal-a-doc/"
    val DISPLAY_MESSAGE_ACTION = "com.signalDoc_doctor.DISPLAY_MESSAGE"
    val PLAY_SERVICES_RESOLUTION_REQUEST = 1234
    val GALLERY_REQ = 1
    val CAMERA_REQ = 2
    val SPLASH_TIMEOUT = 3000
    val TOAST_TIMEOUT = 2000
    val JEALY_BEAM = 18

    val PERMISSION_ID = 99
    const val DATE_CHECK = "2090-12-11"
    val IMAGE_CODE = 1234
    val IMAGE_CODE_LIC = 1890
    const val PROFILE_DATA = "profile_data"
    const val STATUS_OK = 200
    const val DEVICE_TYPE = "1"

    //role ids
    const val ROLE_DOCTOR = 3
    const val ROLE_PATIENT = 4

    //password length
    const val WEAK_LENGHT = 3
    const val STRONG_LENGHT = 8

    //navigation const
    const val HOME = 0
    const val APPOITMENTS = 1
    const val EARNINGS = 2
    const val ACCOUNT = 3

    //basic consts
    const val PASSWORD_LENGHT = 8
    const val PHONE_LENGHT = 5
    const val MALE = 0
    const val FEMALE = 1

    //blood group types
    const val O_NEGATIVE = 0

    //edit const
    const val FIRST_NAME = 1
    const val LAST_NAME = 2
    const val DAT_OF_BIRTH = 3
    const val ADDRESS = 4
    const val COUNTRY = 5
    const val PHONE = 6
    const val BLOOD_TYPE = 7
    const val WEIGHT = 8
    const val HEIGHT = 9
    const val ALERGIES = 10
    const val MEDITATIONS = 11

    val MDCN_CERTIFICATE_YES = 1
    val MDCN_CERTIFICATE_NO = 0

    val PROFESSIONAL_STATUS = 12
    val FOLIO_NUMBER = 13
    val REGISTRATION_NUMBER = 14
    val ISSUE_DATE_PROFILE = 15
    val EXPIRY_DATE_PROFILE = 16
    val AREA_SPECILIZATION = 17
    val PROFILE_SUMMERY = 18

    //booking states
    const val STATE_INACTIVE = 0

    const  val STATE_ACTIVE = 1

    const  val STATE_DELETED = 2

    const val STATE_PENDING = 3

    const  val STATE_ACCEPT = 4

    const val STATE_REJECT = 5

    const val STATE_COMPLETED = 6

    const  val STATE_RESCHEDULE = 8

    const  val STATE_CANCELLED = 7

    const val  STATE_RESCHEDULE_PENDING = 9

    const val  STATE_RESCHEDULE_REJECT = 10



    //doctor apis
    const val API_LOGIN = "api/user/login"
    const val API_LOGOUT = "api/user/logout"
    const val API_USER_CHECK = "api/user/check"
    const val API_MY_PROFILE = "api/user/my-profile"
    const val API_UPDATE_PROFILE = "api/user/update-profile"

    //doctor apis

    const val API_AREA_OF_SPECILIZATION = "api/user/department-list"
    const val API_PROFESSIONAL_STATUS_LIST = "api/user/professional-status-list"

    const val API_DOCTOR_SIGNUP = "api/user/doctor-signup"
    const val API_GET_CATEGORIES = "api/user/category-list"
    const val API_GET_ALL_LANGUAGE_LIST = "api/user/language-list"
    const val API_GET_COMMUNICATION_MODE_LIST = "api/user/doctor-consultation-mode" // modes of communication
    const val API_SAVING_DOCTOR_LIST = "api/user/saving-doctor-availability" // modes of communication
    const val API_SAVING_DOCTOR_DATE_LIST = "api/user/save-doctor-availability-dates"
    const val API_PENDING_DOCTOR_APPOINTMENTS_LIST = "api/user/doctor-pending-appointments"
    const val API_UPCOMING_DOCTOR_APPOINTMENTS_LIST = "api/user/upcoming-appointment"
    const val API_CHANGE_APPOITMENT_STATE = "api/user/set-booking-status"
    const val API_APPOITNMENT_DETAIL = "api/appointment/appointment-detail?id="
    const val API_PAST_APPOITNMENT = "api/user/past-appointment"

    const val THOUSAND = 1000
}