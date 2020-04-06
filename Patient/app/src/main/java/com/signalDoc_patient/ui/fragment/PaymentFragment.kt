/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 */

package com.signalDoc_patient.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentPaymentBinding
import com.signalDoc_patient.ui.activity.MainActivity
import com.signalDoc_patient.ui.adapter.CardAdapter
import com.signalDoc_patient.ui.extensions.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*


class PaymentFragment : BaseFragment() {

    private var binding: FragmentPaymentBinding? = null
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var adapterCard: CardAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (baseActivity is MainActivity) {
            baseActivity!!.navBNV.visibility = View.VISIBLE
            baseActivity!!.toolbar.visibility = View.VISIBLE
        }


        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {

        bottomSheet()
        setCardAdapter()

        binding!!.addNewCardTV.setOnClickListener()
        {

            expandCloseSheet()
        }


        binding!!.transactionHistoryIV.setOnClickListener() {
            baseActivity!!.replaceFragment(TransactionHistoryFragment(), R.id.frame_container)


        }

        binding!!.addCardBottom.addCardBT.setOnClickListener()
        {
            if (baseActivity is MainActivity) {
                baseActivity!!.replaceFragment(CardAddedFragment(), R.id.frame_container)
            }
        }


    }

    private fun setCardAdapter() {

        adapterCard = CardAdapter(baseActivity)
        binding!!.cardRV.adapter = adapterCard

    }


    private fun bottomSheet() {
        sheetBehavior = BottomSheetBehavior.from<ConstraintLayout>(binding!!.addCardBottom.addCardBS)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if(BuildConfig.DEBUG){
                Log.e("onSlide", "onSlide")
            }}
        })

    }

    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)

    }
}

