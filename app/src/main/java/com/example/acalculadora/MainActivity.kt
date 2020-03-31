package com.example.acalculadora

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import kotlin.collections.ArrayList


private val TAG = MainActivity::class.java.simpleName
private var lastCal = "0"
private var aux = ""
private var hasCalculated = true
private var historicMode = false
private var VISOR_KEY = "visor"
private var historic: ArrayList<Operation> = arrayListOf(Operation("1+1", 2.0))
const val EXTRA_HISTORIC_LIST = "com.example.acalculator.LIST"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "o método \"onCreate\" foi invocado")
        setContentView(R.layout.activity_main)

        updateHistoric()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list_historic.setOnItemClickListener { parent, view, position, id ->
                val element = list_historic.adapter.getItem(position)
//                val intent = Intent(
//                    this,
//                    Main2Activity::class.java
//                )
//                startActivity(intent)
                makeToast("$element ")
            }
        }

    }

    override fun onDestroy() {
        Log.i(TAG, "o método \"onDestroy\" foi invocado")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(VISOR_KEY, text_visor.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text_visor.text = savedInstanceState?.getString(VISOR_KEY)
    }

    private fun updateHistoric() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list_historic.adapter = HistoricAdapter(
                this,
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
        Toast.makeText(this, text +
                "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:" +
                "${Calendar.getInstance().get(Calendar.MINUTE)}:" +
                "${Calendar.getInstance().get(Calendar.SECOND)}",
            Toast.LENGTH_SHORT).show()
    }

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

    fun onClickClear(view: View) {
        Log.i(TAG, "Click on button C")
        text_visor.text = "0"
        if (!hasCalculated) {
            hasCalculated = true
        }
        aux = ""
        makeToast("button_c.setOnClickListener at ")
    }

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
            val element = list_historic.adapter.getItem(view.verticalScrollbarPosition/*position*/)
            makeToast("$element ")
//        }
    }

    fun onClickSendToHistoricPage(view: View) {
        makeToast("historic page ")
        val intent = Intent(
            this,
            Main2Activity::class.java
        )
        intent.apply {
            putExtra(
                EXTRA_HISTORIC_LIST,
                historic
            )
        }
        startActivity(intent)
        finish()
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
