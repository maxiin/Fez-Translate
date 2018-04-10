package com.max.tgr.feztranslate.fragments

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import com.max.tgr.feztranslate.R
import kotlinx.android.synthetic.main.fragment_symbols.symbol_main_view
import kotlinx.android.synthetic.main.fragment_symbols.symbol_command_view
import kotlinx.android.synthetic.main.fragment_symbols.view.symbol_keyboard_row1
import kotlinx.android.synthetic.main.fragment_symbols.view.symbol_keyboard_row2
import kotlinx.android.synthetic.main.fragment_symbols.view.symbol_command_view

class SymbolsFragment : Fragment() {

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

    private fun onClick(id: Int){

        getController()
        val idName = resources.getResourceEntryName(id)

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
            if(textToPrintP.isNotEmpty()) {
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
        val fezFont = Typeface.createFromAsset(context.assets, "fonts/Fez.ttf")

        for (i in 0 until out.symbol_keyboard_row1.childCount) {
            val view = out.symbol_keyboard_row1.getChildAt(i)
            if (view is TextView) {
                view.typeface = fezFont
                view.setOnClickListener { onClick(view.id) }
            }
        }
        for (i in 0 until out.symbol_keyboard_row2.childCount) {
            val view = out.symbol_keyboard_row2.getChildAt(i)
            if (view is TextView) {
                if(i != out.symbol_keyboard_row2.childCount - 1)
                view.typeface = fezFont
                view.setOnClickListener { onClick(view.id) }
            }
        }

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
