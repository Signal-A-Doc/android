package com.signalDoc_patient.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.signalDoc_patient.BuildConfig
import com.signalDoc_patient.R
import com.signalDoc_patient.databinding.FragmentPastAppointmentBinding
import com.signalDoc_patient.model.AppoitnmemtsListData
import com.signalDoc_patient.ui.adapter.BaseAdapter
import com.signalDoc_patient.ui.adapter.PastAppointmentAdapter
import com.signalDoc_patient.ui.extensions.visibleView
import com.signalDoc_patient.viewModel.BookingViewModell.BookingModel

class PastAppointmentFragment : BaseFragment(), BaseAdapter.OnItemClickListener {

    private var binding: FragmentPastAppointmentBinding? = null
    private var pastAdapter: PastAppointmentAdapter? = null
    private lateinit var model: BookingModel
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this)[BookingModel::class.java]
        model.setData(baseActivity!!, restFullClient!!, api!!, this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_past_appointment, container, false)
        }
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        bottomSheet()
        val observerObj: Observer<ArrayList<AppoitnmemtsListData>> = Observer {
            pastAdapter = PastAppointmentAdapter(baseActivity!!, it, this)
            binding!!.pastAppointmentRV.adapter = pastAdapter
            binding!!.pastAppointmentRV.visibleView(it.isNotEmpty())
        }
        model.apipastAppoitnmnetsList().observe(this, observerObj)

    }


    private fun bottomSheet() {

        sheetBehavior = BottomSheetBehavior.from<ConstraintLayout>(binding!!.detailBottom.detailSheet)

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {


            override fun onStateChanged(bottomSheet: View, newState: Int) { // React to state change
                if (BuildConfig.DEBUG) {
                    Log.e("onStateChanged", "onStateChanged:$newState")
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) { // React to dragging events
                if (BuildConfig.DEBUG) {
                    Log.e("onSlide", "onSlide")
                }
            }
        })

    }


    private fun expandCloseSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    override fun onItemClick(vararg itemData: Any) {
        expandCloseSheet()
    }

}