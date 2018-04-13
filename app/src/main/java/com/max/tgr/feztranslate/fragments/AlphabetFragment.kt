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

    /*
    these are the variables for printing in the right places on the screen
    onMain is the translated part and the Symbols is the symbols that the person wrote
    on the keyboard
    */
    var printOnMain = ""
    var printSymbols = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_alphabet, container, false)

        //setupButtons will set the right font and click listeners
        //passing the layout because of the fragment wiew
        setupButtons(layout)

        return layout
    }

    private fun setupButtons(out: View) {
        //loading the custom font
        val fezFont = Typeface.createFromAsset(context.assets, "fonts/Fez.ttf")

        //getting the size of each keyboard row (how many button in each of them)
        val row1Count = out.alphabet_keyboard_row1.childCount
        val row2Count = out.alphabet_keyboard_row2.childCount
        val row3Count = out.alphabet_keyboard_row3.childCount
        val row4Count = out.alphabet_keyboard_row4.childCount
        val totalButtons = row1Count + row2Count + row3Count + row4Count

        //for loop for setting the font and the listener for each button
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

            //checking if is a text view, and setting its font and click listener
            if (view is TextView) {
                view.typeface = fezFont
                view.setOnClickListener { onClick(view.id) }
            }
        }

        //setting the side textView for using the custom font
        out.alphabet_symbol_view.typeface = fezFont
        //setting the special buttons, clear and erase, their onClick listeners
        out.alphabet_button_clear.setOnClickListener { onClick(out.alphabet_button_clear.id) }
        out.alphabet_button_erase.setOnClickListener { onClick(out.alphabet_button_erase.id) }

        //on click listener for hiding and showing the keyboard using the "hide" or "show" button
        out.alphabet_button_hide.setOnClickListener {

            if (out.alphabet_keyboard_main.height > 0) {
                //for hiding the height is set to 0, and the button changes to "show"
                out.alphabet_keyboard_main.layoutParams.height = 0
                out.alphabet_button_hide.text = getString(R.string.button_show)
            } else {
                //for showing the button gets back on match parent and the button shows "hide"
                out.alphabet_keyboard_main.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
                out.alphabet_button_hide.text = getString(R.string.button_hide)
            }

            //commit the changes
            out.alphabet_keyboard_main.requestLayout()

        }

    }

    private fun onClick(id: Int) {
        //gets the actual name of the id
        val idName = resources.getResourceEntryName(id)


        when (idName) {
            //if is the erase button, clear the last character
            //every part of this "when" will change the main textView AND the symbol textView
            "alphabet_button_erase" -> {
                if (printOnMain.isNotEmpty()) {

                    //as there are symbols that mean 2 letters and they are print as (xy) we need to
                    //erase 4 characters instead of 1 sometimes
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
            //clear vars
            "alphabet_button_clear" -> {
                printOnMain = ""
                printSymbols = ""
            }
            //this one adds two letters as some symbols does that
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
            //this one is for the majority of the buttons
            else -> {
                printOnMain += idName.last()
                printSymbols += idName.last()
            }
        }
        //setting the changed variables to their views
        activity.alphabet_main_view.text = printOnMain
        activity.alphabet_symbol_view.text = printSymbols
    }

}
