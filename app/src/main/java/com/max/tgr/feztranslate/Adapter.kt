package com.max.tgr.feztranslate

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.max.tgr.feztranslate.fragments.AlphabetFragment
import com.max.tgr.feztranslate.fragments.PlaygroundFragment
import com.max.tgr.feztranslate.fragments.SymbolsFragment

class Adapter : FragmentPagerAdapter{

    constructor(fm: FragmentManager?) : super(fm)

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AlphabetFragment()
            1 -> SymbolsFragment()
            2 -> PlaygroundFragment()
            else -> AlphabetFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Alphabet"
            1 -> "Symbols"
            2 -> "Playground"
            else -> "Alphabet"
        }
    }

}