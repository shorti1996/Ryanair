package com.wojciszke.ryanair.view.searchresult

import android.widget.SeekBar

class SeekBarListener(val onStopTrackingTouchBlock: (seekBar: SeekBar) -> Unit) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // stub
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        seekBar?.let { onStopTrackingTouchBlock(it) }
    }
}