package com.example.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INIT_TIP_PERC = 15

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarTip.progress = INIT_TIP_PERC
        tvPercentage.text = "$INIT_TIP_PERC%"
        updateTipDesc(INIT_TIP_PERC)
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgessChanged $progress")
                tvPercentage.text = "$progress%"
                updateTipDesc(progress)
                computeTipAndTotal()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        etBase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }
        })
    }
    private fun computeTipAndTotal() {
        if (etBase.text.isEmpty()) {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        val tipAmount = etBase.text.toString().toDouble() * seekBarTip.progress / 100
        val totalAmount = tipAmount + etBase.text.toString().toDouble()
        tvTotalAmount.text = "%.2f".format(totalAmount)
        tvTipAmount.text = "%.2f".format(tipAmount)

    }
    private fun updateTipDesc(tipPercent: Int) {
        val tipDesc : String
        when (tipPercent) {
            in 0..9 -> tipDesc = "\uD83D\uDE2D"
            in 10..14 -> tipDesc = "\uD83D\uDE29"
            in 15..19 -> tipDesc = "\uD83D\uDE10"
            in 20..24 -> tipDesc = "\uD83D\uDE0A"
            else -> tipDesc = "\uD83E\uDD70"
        }
        tvTipDesc.text = tipDesc
    }
}