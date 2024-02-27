package com.burakdemir.ExziCase.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.burakdemir.ExziCase.view.SpotFragment

class TradeViewPagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpotFragment()
            //TODO(): MARGIN AND CONVERT FRAGMENT
            else -> Fragment()
        }
    }
}