package com.example.networkingussd

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.networkingussd.adapters.HomePagerAdapter
import com.example.networkingussd.databinding.FragmentHomeBinding
import com.example.networkingussd.network.LocaleHelper

class HomeFragment : Fragment() {

    lateinit var fraging: FragmentHomeBinding
    lateinit var homePagerAdapter: HomePagerAdapter
    lateinit var helloworld: TextView
    lateinit var dialog_language: TextView
    var lang_selected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentHomeBinding.inflate(inflater, container, false)
        var context: Context
        lateinit var resources: Resources
        fraging.apply {
            homePagerAdapter = HomePagerAdapter(requireParentFragment())
            pager2.adapter = homePagerAdapter
            pager2.isUserInputEnabled = false

            pager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        bottom.selectedItemId = R.id.home
                    } else {
                        bottom.selectedItemId = R.id.second
                    }
                }
            })

            bottom.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> pager2.currentItem = 0
                    R.id.second -> pager2.currentItem = 1
                }
                true
            }
        }
        return fraging.root
    }
}