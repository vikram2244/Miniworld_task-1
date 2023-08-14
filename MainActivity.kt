package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var billAmountEditText: EditText
    private lateinit var tipPercentageRadioGroup: RadioGroup
    private lateinit var tipAmountTextView: TextView
    private lateinit var totalAmountTextView: TextView
    private lateinit var splitBillAmountTextView: TextView
    private lateinit var personsEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billAmountEditText = findViewById(R.id.bill_amount_edit_text)
        tipPercentageRadioGroup = findViewById(R.id.tip_percentage_radio_group)
        tipAmountTextView = findViewById(R.id.tip_amount_text_view)
        totalAmountTextView = findViewById(R.id.total_amount_text_view)
        splitBillAmountTextView = findViewById(R.id.split_bill_amount_text_view)
        personsEditText = findViewById(R.id.persons_edit_text)

        setupListeners()
        setupTipPercentages()
        val resetButton: Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            resetValues()
        }
    }

    private fun setupListeners() {
        billAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                calculateAndDisplayAmounts()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        tipPercentageRadioGroup.setOnCheckedChangeListener { _, _ ->
            calculateAndDisplayAmounts()
        }
        personsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                calculateAndDisplayAmounts()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun setupTipPercentages() {
        val tipPercentages = listOf(10, 15, 18, 20)
        for (tipPercentage in tipPercentages) {
            val radioButton = RadioButton(this)
            radioButton.text = "$tipPercentage%"
            radioButton.id = tipPercentage
            tipPercentageRadioGroup.addView(radioButton)
        }
    }

    private fun calculateAndDisplayAmounts() {
        val billAmountText = billAmountEditText.text.toString()
        val personsText = personsEditText.text.toString()

        if (billAmountText.isNotBlank() && personsText.isNotBlank()) {
            val billAmount = billAmountText.toFloat()
            val tipPercentage = getSelectedTipPercentage() / 100
            val tipAmount = billAmount * tipPercentage
            val totalAmount = billAmount + tipAmount
            val persons = personsText.toInt()

            tipAmountTextView.text = getString(R.string.tip_amount, tipAmount)
            totalAmountTextView.text = getString(R.string.total_amount, totalAmount)

            val splitBillAmount = totalAmount / persons.toFloat()
            splitBillAmountTextView.text = getString(R.string.split_bill_amount, splitBillAmount)
        } else {
            tipAmountTextView.text = getString(R.string.tip_amount, 0.0f)
            totalAmountTextView.text = getString(R.string.total_amount, 0.0f)
            splitBillAmountTextView.text = getString(R.string.split_bill_amount, 0.0f)
        }
    }

    private fun getSelectedTipPercentage(): Float {
        val selectedRadioButton = findViewById<RadioButton>(tipPercentageRadioGroup.checkedRadioButtonId)
        return selectedRadioButton.text.toString().removeSuffix("%").toFloat()
    }
    private fun resetValues() {
        billAmountEditText.text.clear()
        tipPercentageRadioGroup.clearCheck()
        personsEditText.text.clear()
        tipAmountTextView.text = getString(R.string.tip_amount, 0.0f)
        totalAmountTextView.text = getString(R.string.total_amount, 0.0f)
        splitBillAmountTextView.text = getString(R.string.split_bill_amount, 0.0f)
    }
}
