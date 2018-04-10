package com.max.tgr.feztranslate.fragments

import android.graphics.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.max.tgr.feztranslate.R
import kotlinx.android.synthetic.main.fragment_playground.playground_edit_text
import kotlinx.android.synthetic.main.fragment_playground.playground_main_text
import kotlinx.android.synthetic.main.fragment_playground.view.playground_send_button
import kotlinx.android.synthetic.main.fragment_playground.view.playground_main_text

class PlaygroundFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_playground, container, false)
        val fezFont = Typeface.createFromAsset(context.assets, "fonts/Fez.ttf")

        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        layout.playground_main_text.typeface = fezFont
        layout.playground_send_button.setOnClickListener({
            playground_main_text.text = playground_edit_text.text.toString().toLowerCase()
        })

        return layout
    }

}
