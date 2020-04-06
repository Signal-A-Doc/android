/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.utils

object Const {

    val SELECTED_TIME_SLOTS = "time_slots"
    val DOCTOR_DATA = "doctor_data"
    val IS_OPEN_FROM_TOP_BUTTON = "is_open_from_top_button"
    val OPEN_PAYMENT :Int = 8523

    //val SERVER_REMOTE_URL = "http://192.168.2.175/signal-a-doc-yii2-1347/"
    val SERVER_REMOTE_URL = "http://beta.toxsl.in/signal-a-doc/"
    val DISPLAY_MESSAGE_ACTION = "com.signalDoc_patient.DISPLAY_MESSAGE"
    val PLAY_SERVICES_RESOLUTION_REQUEST = 1234
    val GALLERY_REQ = 1
    val CAMERA_REQ = 2
    val SPLASH_TIMEOUT = 3000
    val TOAST_TIMEOUT = 2000
    val TITLE = "title"
    val ROW_ID = "news_id"
    val PERMISSION_ID = 99
    const val DATE_CHECK = "2090-12-11"
    val IMAGE_CODE = 1234
    const val PROFILE_DATA = "profile_data"
    const val STATUS_OK = 200
    const val DEVICE_TYPE = "1"

    //role ids
    const val ROLE_DOCTOR = 3
    const val ROLE_PATIENT = 4

    //navigation const
    const val HOME = 0
    const val APPOITMENTS = 1
    const val MEDICAL = 2
    const val ACCOUNT = 3

    //basic consts
    const val PASSWORD_LENGHT = 8
    const val PHONE_LENGHT = 5
    const val MALE = 0
    const val FEMALE = 1

    //blood group types
    const val O_NEGATIVE = 0

    //password length
    const val WEAK_LENGHT = 3
    const val STRONG_LENGHT = 8

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

    //patient apis
    const val API_PATIENT_SIGNUP = "api/user/patient-signup"
    const val API_GET_CATEGORIES = "api/user/category-list"
    const val API_GET_SYMPTOMS_LIST = "api/user/get-category"
    const val API_LOGIN = "api/user/login"
    const val API_LOGOUT = "api/user/logout"
    const val API_USER_CHECK = "api/user/check"
    const val API_MY_PROFILE = "api/user/my-profile"
    const val API_UPDATE_PROFILE = "api/user/update"
    const val API_SAVE_HEALTH_PROFILE = "api/user/health-profile"

    //home screen apis
    const val API_GET_DOCTOR_LIST = "api/user/doctor-list"
    const val API_GET_DEPARTMENT_LIST = "api/user/department-list"
    const val API_FAV_DOCTOR = "api/user/favorite"
    const val API_RATE_DOCTOR = "api/user/add-doctor-rating"
    const val API_MEDICAL_PROFESSIONALS_DOCTORS_LIST = "api/user/doctor-search"
    const val API_PROFESSIONAL_STATUS_LIST = "api/user/department-list"

    const val API_GET_QUESTIONS_LIST = "api/category-question/question-list"//?id=
    const val API_GET_MEDICAL_CONDITIONS = "api/user/medical-conditions"
    const val API_GET_AVAILABILITY_MODE_LIST = "api/user/doctor-consultation-availability"
    const val API_GET_DOCTOR_DATE_LIST = "api/user/get-doctor-dates"
    const val API_GET_DOCTOR_TIME_SLOTS_LIST = "api/user/get-doctor-slots"

    //booking apis
    const val API_BOOK_APPOINTMENT_LIST = "api/user/book-appointment"
    const val API_UPCOMING_APPOINTMENT_LIST = "api/appointment/my-upcoming-appointments"
    const val API_PAST_APPOINTMENT_LIST = "api/appointment/my-past-appointments"
    const val API_APPOINTMENT_DETAIL = "api/appointment/appointment-detail?id="

}