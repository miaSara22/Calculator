package com.miaekebom.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var numberInput: Boolean = false
    private var dotInput: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigitClick(view: View) {
        tvInput.append((view as Button).text)
        numberInput = true
        dotInput = false
    }

    fun onClearClick(view: View) {
        tvInput.text = ""
    }

    fun onDecimalPointClick(view: View) {
        if (numberInput && !dotInput){
            tvInput.append(".")
            numberInput = false
            dotInput = true
        }
    }

    fun onOperatorClick(view: View){
        if (numberInput && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            numberInput = false
            dotInput = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
        }
    }

    fun onEqualClick(view: View){
        if (numberInput){
            val tvValue = tvInput.text.toString()
            var prefix =""

            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue.substring(1)
                }

                //reduction action
                if (tvValue.contains("-")) {

                    val splitValue = tvValue.split("-")
                    var first = splitValue[0]
                    val second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput.text = removeUnnecessaryDotZero((first.toDouble() - second.toDouble()).toString())
                }

                //add operation
                else if(tvValue.contains("+")){

                    val splitValue = tvValue.split("+")
                    var first = splitValue[0]
                    val second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput.text = removeUnnecessaryDotZero((first.toDouble() + second.toDouble()).toString())

                }

                //multiplication operation
                else if (tvValue.contains("*")){

                    val splitValue = tvValue.split("*")
                    var first = splitValue[0]
                    val second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput.text = removeUnnecessaryDotZero((first.toDouble() * second.toDouble()).toString())
                }

                //division
                else if (tvValue.contains("/")){

                    val splitValue = tvValue.split("/")
                    var first = splitValue[0]
                    val second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput.text = removeUnnecessaryDotZero((first.toDouble() / second.toDouble()).toString())
                }

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeUnnecessaryDotZero(result: String): String{
        var value = result
        if (result.endsWith(".0"))
            value = result.substring(0, result.length -2)
        return value
    }

    fun onDelOneSpaceClick(view: View) {
        if (tvInput.text.isNotEmpty()) {
            val value = tvInput.text.substring(0, tvInput.length()-1)
            tvInput.text = value
        }
    }
}