package com.max.tgr.feztranslate.fragments

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.max.tgr.feztranslate.R
import kotlinx.android.synthetic.main.fragment_alphabet.view.*
import kotlinx.android.synthetic.main.fragment_symbols.*
import kotlinx.android.synthetic.main.fragment_symbols.view.*

class SymbolsFragment : Fragment() {

    //todo:change these variables
    var textToPrintK:String = ""
    var textToPrintP:String = ""

    var up:String = "Up"
    var right:String = "Right"
    var down:String = "Down"
    var left:String = "Left"
    var rotateLeft:String = "LT"
    var rotateRight:String = "RT"
    var jump:String = "Jump"

    private var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout =  inflater.inflate(R.layout.fragment_symbols, container, false)

        prefs = this.activity.getSharedPreferences("prefsFile", 0)

        setupButtons(layout)
        getController()

        return layout

    }

    private fun switchOnClick(text: String){

        val key = getString(R.string.symbols_toKey)
        val num = getString(R.string.symbols_toNumber)

        Log.e("switch",text)

        if(text == key){
            symbol_keyboard_rows.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
            symbol_number_rows.layoutParams.height = 0
            symbol_button_switch.text = num
        }else{
            symbol_keyboard_rows.layoutParams.height = 0
            symbol_number_rows.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
            symbol_button_switch.text = key
        }

        symbol_number_rows.requestLayout()
        symbol_keyboard_rows.requestLayout()

    }

    private fun numsOnClick(id: Int){

        val idName = resources.getResourceEntryName(id)

        when (idName.last()) {
            'e' ->{
                if(textToPrintK.isNotEmpty() && textToPrintP.isNotEmpty()) {
                    textToPrintK = textToPrintK.substring(0, textToPrintK.length - 1)
                    textToPrintP = textToPrintP.substring(0, textToPrintP.length - 1)
                }
            }
            else ->{
                textToPrintK += idName.last()
                textToPrintP += idName.last()
            }

        }

        activity.symbol_main_view.text = textToPrintK
        activity.symbol_command_view.text = textToPrintP

    }

    private fun eraseOnClick(){

        if(textToPrintK.isNotEmpty() || textToPrintP.isNotEmpty()) {

            when (textToPrintP.substring(textToPrintP.length - 1, textToPrintP.length)) {
                "A" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (up.length + 2))
                "B" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (right.length + 2))
                "C" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (down.length + 2))
                "D" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (left.length + 2))
                "E" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (rotateLeft.length + 2))
                "F" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (rotateRight.length + 2))
                "G" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (jump.length + 2))
                else -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - 1)
            }
            textToPrintP = textToPrintP.substring(0, textToPrintP.length - 1)

            activity.symbol_main_view.text = textToPrintK
            activity.symbol_command_view.text = textToPrintP

        }

    }

    private fun keysOnClick(id: Int){

        val idName = resources.getResourceEntryName(id)

        getController()

        if(idName.last() != 'e') {
            when (idName.last()) {
                'A' -> textToPrintK += up
                'B' -> textToPrintK += right
                'C' -> textToPrintK += down
                'D' -> textToPrintK += left
                'E' -> textToPrintK += rotateLeft
                'F' -> textToPrintK += rotateRight
                'G' -> textToPrintK += jump
            }

            textToPrintK += ", "
            textToPrintP += idName.last()
        }else{
            if(textToPrintK.isNotEmpty() && textToPrintP.isNotEmpty()) {
                Log.e("erase", textToPrintP.substring(textToPrintP.length - 1, textToPrintP.length))
                when (textToPrintP.substring(textToPrintP.length - 1, textToPrintP.length)) {
                    "A" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (up.length + 2))
                    "B" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (right.length + 2))
                    "C" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (down.length + 2))
                    "D" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (left.length + 2))
                    "E" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (rotateLeft.length + 2))
                    "F" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (rotateRight.length + 2))
                    "G" -> textToPrintK = textToPrintK.substring(0, textToPrintK.length - (jump.length + 2))
                }
                textToPrintP = textToPrintP.substring(0, textToPrintP.length - 1)
            }
        }

        activity.symbol_main_view.text = textToPrintK
        activity.symbol_command_view.text = textToPrintP
    }

    private fun setupButtons(out:View){
        //custom font
        val fezFont = Typeface.createFromAsset(context.assets, "fonts/Fez.ttf")

        //switch button on click listener
        out.symbol_button_switch.setOnClickListener{ switchOnClick(out.symbol_button_switch.text.toString()) }

        //getting the size of each keyboard row (how many button in each of them)
        val keyRow1Count = out.symbol_keyboard_row1.childCount
        val keyRow2Count = out.symbol_keyboard_row2.childCount
        val totalKeyButtons = keyRow1Count + keyRow2Count

        //for loop for setting the font and the listener for each key button
        for (i in 0 until totalKeyButtons - 1) {
            var view: View

            Log.v("until", i.toString())

            if (i < keyRow1Count) {
                view = out.symbol_keyboard_row1.getChildAt(i)
            } else{
                view = out.symbol_keyboard_row2.getChildAt(i - keyRow1Count)
            }

            //checking if is a text view, and setting its font and click listener
            if (view is TextView) {
                view.typeface = fezFont
                view.setOnClickListener { keysOnClick(view.id) }
            }
        }

        //getting the size of each keyboard row again (how many button in each of them)
        val numRow1Count = out.symbol_number_keyboard_row1.childCount
        val numRow2Count = out.symbol_number_keyboard_row2.childCount
        val numRow3Count = out.symbol_number_keyboard_row3.childCount
        val totalNumButtons = numRow1Count + numRow2Count + numRow3Count

        //for loop for setting the font and the listener for each number button
        for (i in 0 until totalNumButtons - 1) {
            var view: View

            Log.v("until", i.toString())

            if (i < numRow1Count) {
                view = out.symbol_number_keyboard_row1.getChildAt(i)
            } else if (i < numRow1Count + numRow2Count) {
                view = out.symbol_number_keyboard_row2.getChildAt(i - numRow1Count)
            } else{
                view = out.symbol_number_keyboard_row3.getChildAt(i - numRow1Count - numRow2Count)
            }

            //checking if is a text view, and setting its font and click listener
            if (view is TextView) {
                view.typeface = fezFont
                view.setOnClickListener { numsOnClick(view.id) }
            }
        }

        out.symbol_button_erase.setOnClickListener { eraseOnClick() }
        out.symbol_number_button_erase.setOnClickListener { eraseOnClick() }

        out.symbol_command_view.typeface = fezFont
    }

    private fun getController(){

        val controller = prefs!!.getString("Controller", "xbox")

        when (controller){
            "xbox" ->{
                rotateLeft = "LT"
                rotateRight = "RT"
                jump = "A Btn"
            }
            "play" ->{
                rotateLeft = "L2"
                rotateRight = "R2"
                jump = "X Btn"
            }
            "pc" ->{
                rotateLeft = "A "
                rotateRight = "D "
                jump = "Space"
            }
        }

    }

}
