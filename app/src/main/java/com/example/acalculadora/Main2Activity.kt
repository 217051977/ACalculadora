package com.example.acalculadora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val operations = intent.getParcelableArrayListExtra<Operation>(EXTRA_HISTORIC_LIST)
        historic_page.adapter = HistoricAdapter(
            this,
            R.layout.historic_page,
            operations
        )

        button_back.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        }

        historic_page.setOnItemClickListener { parent, view, position, id ->
            val element = historic_page.adapter.getItem(position)
//                val intent = Intent(
//                    this,
//                    Main2Activity::class.java
//                )
//                startActivity(intent)
            Toast.makeText(this, "$element " +
                    "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:" +
                    "${Calendar.getInstance().get(Calendar.MINUTE)}:" +
                    "${Calendar.getInstance().get(Calendar.SECOND)}",
                Toast.LENGTH_SHORT).show()
        }

    }

}
