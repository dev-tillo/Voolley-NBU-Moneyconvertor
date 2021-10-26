package com.example.networkingussd.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.networkingussd.R
import com.example.networkingussd.adapters.HomeAdapter
import com.example.networkingussd.classes.MoneyItem
import com.example.networkingussd.databinding.BottomsheetBinding
import com.example.networkingussd.databinding.FragmentPagerBinding
import com.example.networkingussd.network.NetworkHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.alterac.blurkit.BlurLayout

class Pager : Fragment() {

    lateinit var fraging: FragmentPagerBinding
    lateinit var homeAdapter: HomeAdapter
    lateinit var requestQueue: RequestQueue
    private var islike = -1

    @SuppressLint("NotifyDataSetChanged", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentPagerBinding.inflate(inflater, container, false)
        fraging.apply {
            requestQueue = Volley.newRequestQueue(requireContext())
            val network = NetworkHelper(requireContext())
            if (network.isNetworkConnected()) {
                homeAdapter = HomeAdapter(object : HomeAdapter.OnitemCliked {
                    override fun onCliked(money: MoneyItem, position: Int) {

                    }

                    override fun cardClick(money: MoneyItem, position: Int) {
                        val dialog = AlertDialog.Builder(requireContext())
                        val binding_dialog = BottomsheetBinding.inflate(layoutInflater)
                        val create = dialog.create()
                        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        create.setView(binding_dialog.root)
                        binding_dialog.apply {
                            name.setText(money.CcyNm_UZ)
                            xa.setOnClickListener {
                                var its = money.Rate
                                val sum = text.text.toString()
                                if (sum == "") {
                                    Toast.makeText(requireContext(),
                                        "Maydonni to`ldiring",
                                        Toast.LENGTH_SHORT).show()
                                } else {
                                    val number = text.text.toString()
                                    var num = number.toDouble() * its.toDouble()
                                    nummss.text = num.toString()
                                }
                            }
                            create.dismiss()
                        }
                        create.show()
                    }
                })
                rvc.adapter = homeAdapter
                val jsonArrayRequest =
                    JsonArrayRequest(Request.Method.GET,
                        "https://cbu.uz/uz/arkhiv-kursov-valyut/json/",
                        null,
                        { response ->
                            val str = response.toString()
                            val type = object : TypeToken<List<MoneyItem>>() {}.type
                            val userList = Gson().fromJson<List<MoneyItem>>(str, type)
                            for (i in userList.indices) {
                                userList[i].image = "https://flagcdn.com/w160/" +
                                        userList[i].Ccy[0].lowercase() + userList[i].Ccy[1].lowercase() + ".png"
                            }
                            homeAdapter.submitList(userList)
                        }
                    ) { error ->
                        Log.d(TAG,
                            "onErrorResponse: ${error?.message}")
                    }
                requestQueue.add(jsonArrayRequest)

            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
        return fraging.root
    }
}