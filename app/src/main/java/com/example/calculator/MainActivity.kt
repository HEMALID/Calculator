package com.example.calculator

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastDot: Boolean=false
    var lastNumeric: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric=true
        lastDot=false

    }

    fun onClear(view: View) {
        binding.tvInput.text=""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            if (binding.tvInput.text != null) {
                if (binding.tvInput.text.toString().contains(".")) {
                    Log.d(ContentValues.TAG,"From Dot1")
                } else {
                    binding.tvInput.append(".")
                }
            }
            lastNumeric=false
            lastDot=true
        }
    }

    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
        }
    }

    fun onOperator(view: View) {
        binding.tvInput.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                binding.tvInput.append((view as Button).text)
                lastNumeric=false
                lastDot=true
            }

        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                when {
                    tvValue.contains("/") -> {
                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitedValue = tvValue.split("*")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {

                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {

                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}