package com.example.acalculadora

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*
import kotlinx.android.synthetic.main.fragment_historic.view.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

private val TAG = CalculatorFragment::class.java.simpleName
private var lastCal = "0"
private var aux = ""
private var hasCalculated = true
private var historicMode = false
private var VISOR_KEY = "visor"
private var historic: ArrayList<Operation> = arrayListOf(Operation("1+1", 2.0))
const val EXTRA_HISTORIC_LIST = "com.example.acalculator.LIST"

class CalculatorFragment : Fragment() {

    lateinit var communicator: Communicator

    //    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        updateHistoric()

        val view = inflater.inflate(
            R.layout.fragment_calculator,
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
        updateHistoric()
    }

    private fun updateHistoric() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            var list_historic = findViewById(R.id.list_historic)
            list_historic.layoutManager = LinearLayoutManager(activity as Context)
            list_historic.adapter = HistoricAdapter(
                activity as Context,
                R.layout.item_expression,
//                arrayListOf(
//                    "1+1=2",
//                    "2+3=5"
//                )
                historic
            )
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(activity as Context, text +
                "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:" +
                "${Calendar.getInstance().get(Calendar.MINUTE)}:" +
                "${Calendar.getInstance().get(Calendar.SECOND)}",
            Toast.LENGTH_SHORT).show()
    }

    @Optional
    @OnClick(
        R.id.button_00
    )
    fun onClickNumber00(view: View) {
        val number = view.tag.toString()
        Log.i(TAG, "Click on button $number")
        if (text_visor.text.toString() == "0") {
            if (hasCalculated) {
                hasCalculated = false
            }
            text_visor.text = number
        } else {
            text_visor.append(number)
        }
        aux = text_visor.text.toString()
        makeToast("button_$number.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_0,
        R.id.button_1,
        R.id.button_2,
        R.id.button_3,
        R.id.button_4,
        R.id.button_5,
        R.id.button_6,
        R.id.button_7,
        R.id.button_8,
        R.id.button_9
    )
    fun onClickNumber(view: View) {
        val number = view.tag.toString()
        Log.i(TAG, "Click on button $number")
        if (text_visor.text.toString() == "0") {
            if (hasCalculated) {
                hasCalculated = false
            }
            text_visor.text = number
        } else {
            text_visor.append(number)
        }
        aux = text_visor.text.toString()
        makeToast("button_$number.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_equals
    )
    fun onClickEquals(view: View) {
        Log.i(TAG, "Click on button =")
        val textVisorValue = text_visor.text.toString()
        val expression =  ExpressionBuilder(textVisorValue).build().evaluate()
        historic.add(Operation(textVisorValue, expression))
        updateHistoric()
        text_visor.text = expression.toString()
        Log.i(TAG, "The result of the expression is ${text_visor.text}")
        hasCalculated = true
        aux = ""
        makeToast("button_equals.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_addiction,
        R.id.button_subtraction,
        R.id.button_multiplier,
        R.id.button_divider
    )
    fun onClickSymbol(view: View) {
        val symbol = view.tag.toString()
        Log.i(TAG, "Click on button $symbol")
        text_visor.append(symbol)
        if (hasCalculated) {
            hasCalculated = false
        }
        aux = text_visor.text.toString()
        makeToast("button_addiction.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_dot
    )
    fun onClickDot(view: View) {
        Log.i(TAG, "Click on button .")

        if (!text_visor.text.contains(".")) {
            text_visor.append(".")
            if (hasCalculated) {
                hasCalculated = false
            }
        }
        aux = text_visor.text.toString()
        makeToast("button_dot.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_c
    )
    fun onClickClear(view: View) {
        Log.i(TAG, "Click on button C")
        text_visor.text = "0"
        if (!hasCalculated) {
            hasCalculated = true
        }
        aux = ""
        makeToast("button_c.setOnClickListener at ")
    }

    @OnClick(
        R.id.button_delete
    )
    fun onClickDelete(view: View) {
        Log.i(TAG, "Click on button \u232B")
        val expression = text_visor.text
        if (expression.length > 1) {
            text_visor.text = expression.substring(0, expression.length - 1)
            aux = text_visor.text.toString()

            if (hasCalculated) {
                hasCalculated = false
            }
        } else {
            text_visor.text = "0"
            aux = ""

            if (!hasCalculated) {
                hasCalculated = true
            }
        }
        makeToast("button_delete.setOnClickListener at ")
    }

    fun onClickShowExpressionToast(view: View) {
//        view.verticalScrollbarPosition
//        list_historic.setOnItemClickListener { parent, view, position, id ->
//            val element = list_historic.adapter.getItem(view.verticalScrollbarPosition/*position*/)
//            makeToast("$element ")
//        }
    }

    @Optional
    @OnClick(
        R.id.button_historic
    )
    fun onClickSendToHistoricPage(view: View) {
        makeToast("historic page ")

//        communicator = activity as Communicator

//        communicator.passDataComm(view.historic_page.adapter.getList)

        NavigationManager.goToHistoricFragment(activity?.supportFragmentManager!!)
    }

    fun onClickHistoric(view: View) {
        Log.i(TAG, "Click on button \u23F3")
        if (historicMode) {
            text_visor.text = aux
            if (aux != "") {
                if (hasCalculated) {
                    hasCalculated = false
                }
            }
            historicMode = false
        } else {
            text_visor.text = lastCal
            historicMode = true
        }
        makeToast("button_historic.setOnClickListener at ")
    }

}
