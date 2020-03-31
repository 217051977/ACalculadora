package com.example.acalculadora

import android.content.res.Configuration
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*

@Parcelize
class Operation(val expression: String, val result: Double) : AppCompatActivity(), Parcelable {

//    fun updateList(orientation: Int) {
//
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            list_historic.adapter = HistoricAdapter(
//                this,
//                R.layout.item_expression,
////                arrayListOf(
////                    "1+1=2",
////                    "2+3=5"
////                )
//                historic
//            )
//        }
//
//    }

}