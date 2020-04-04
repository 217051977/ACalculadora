package com.example.acalculadora

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_historic.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HistoricFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        updateHistoric()

        val view = inflater.inflate(
            R.layout.fragment_historic,
            container,
            false
        )



        ButterKnife.bind(
            this,
            view
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val operations = activity?.intent!!.getParcelableArrayListExtra<Operation>(EXTRA_HISTORIC_LIST)

        historic_page.layoutManager = LinearLayoutManager(activity as Context)

        historic_page.adapter = HistoricAdapter(
            activity as Context,
            R.layout.historic_page,
//            operations
            mutableListOf(Operation("5+2", 20.0))
        )
    }

    @OnClick(
        R.id.button_back
    )
    fun onClickSendBack(view: View) {
        makeToast("back ")
        NavigationManager.goToCalculatorFragment(activity?.supportFragmentManager!!)
    }

    private fun makeToast(text: String) {
        Toast.makeText(activity as Context, text +
                "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:" +
                "${Calendar.getInstance().get(Calendar.MINUTE)}:" +
                "${Calendar.getInstance().get(Calendar.SECOND)}",
            Toast.LENGTH_SHORT).show()
    }

}
