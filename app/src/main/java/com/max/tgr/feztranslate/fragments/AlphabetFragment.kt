package com.max.tgr.feztranslate.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.max.tgr.feztranslate.R
import kotlinx.android.synthetic.main.fragment_alphabet.alphabet_main_view
import kotlinx.android.synthetic.main.fragment_alphabet.alphabet_symbol_view
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_keyboard_row1
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_keyboard_row2
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_keyboard_row3
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_keyboard_row4
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_symbol_view
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_button_clear
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_button_erase
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_button_hide
import kotlinx.android.synthetic.main.fragment_alphabet.view.alphabet_keyboard_main

class AlphabetFragment : Fragment() {

    var printOnMain = ""
    var printSymbols = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_alphabet, container, false)

        setupButtons(layout)

        return layout
    }

    private fun onClick(id: Int) {
        val idName = resources.getResourceEntryName(id)

        when (idName) {
            "alphabet_button_erase" -> {
                if (printOnMain.isNotEmpty()) {

                    if (printOnMain.substring(printOnMain.length - 1, printOnMain.length) == ")") {
                        printOnMain = printOnMain.substring(0, printOnMain.length - 4)
                    } else {
                        printOnMain = printOnMain.substring(0, printOnMain.length - 1)
                    }

                }
                if (printSymbols.isNotEmpty()) {
                    printSymbols = printSymbols.substring(0, printSymbols.length - 1)
                }
            }
            "alphabet_button_clear" -> {
                printOnMain = ""
                printSymbols = ""
            }
            "alphabet_button_kq", "alphabet_button_uv" -> {
                printOnMain += "(" + idName.substring(idName.length - 2, idName.length) + ")"
                printSymbols += idName.last()
            }
            "alphabet_button_space" -> {
                printOnMain += " "
                printSymbols += " "
            }
            "alphabet_button_comma" -> {
                printOnMain += ","
                printSymbols += ","
            }
            "alphabet_button_period" -> {
                printOnMain += "."
                printSymbols += "."
            }
            else -> {
                printOnMain += idName.last()
                printSymbols += idName.last()
            }
        }
        activity.alphabet_main_view.text = printOnMain
        activity.alphabet_symbol_view.text = printSymbols
    }

    private fun setupButtons(out: View) {
        val fezFont = Typeface.createFromAsset(context.assets, "fonts/Fez.ttf")

        val row1Count = out.alphabet_keyboard_row1.childCount
        val row2Count = out.alphabet_keyboard_row2.childCount
        val row3Count = out.alphabet_keyboard_row3.childCount
        val row4Count = out.alphabet_keyboard_row4.childCount
        val totalButtons = row1Count + row2Count + row3Count + row4Count

        Log.e("buttons", totalButtons.toString() + ", 1 " + row1Count.toString() + ", 2 " + row2Count.toString()
                + ", 3 " + row3Count.toString() + ", 4 " + row4Count.toString())

        for (i in 0 until totalButtons) {
            var view: View

            Log.v("until", i.toString())

            if (i < row1Count) {
                view = out.alphabet_keyboard_row1.getChildAt(i)
            } else if (i < row1Count + row2Count) {
                view = out.alphabet_keyboard_row2.getChildAt(i - row1Count)
            } else if (i < row1Count + row2Count + row3Count) {
                view = out.alphabet_keyboard_row3.getChildAt(i - row1Count - row2Count)
            } else {
                view = out.alphabet_keyboard_row4.getChildAt(i - row1Count - row2Count - row3Count)
            }

            if (view is TextView) {
                view.typeface = fezFont
                view.setOnClickListener { onClick(view.id) }
            }
        }

        out.alphabet_symbol_view.typeface = fezFont
        out.alphabet_button_clear.setOnClickListener { onClick(out.alphabet_button_clear.id) }
        out.alphabet_button_erase.setOnClickListener { onClick(out.alphabet_button_erase.id) }

        out.alphabet_button_hide.setOnClickListener {

            if (out.alphabet_keyboard_main.height > 0) {
                out.alphabet_keyboard_main.layoutParams.height = 0
                out.alphabet_button_hide.text = getString(R.string.button_show)
            } else {
                out.alphabet_keyboard_main.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
                out.alphabet_button_hide.text = getString(R.string.button_hide)
            }

            out.alphabet_keyboard_main.requestLayout()

        }

    }

}
