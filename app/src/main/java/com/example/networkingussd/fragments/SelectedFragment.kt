package com.example.networkingussd.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.networkingussd.adapters.HomeAdapter
import com.example.networkingussd.classes.MoneyItem
import com.example.networkingussd.databinding.BottomsheetBinding
import com.example.networkingussd.databinding.FragmentSelectedBinding
import com.example.networkingussd.network.NetworkHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SelectedFragment : Fragment() {

    lateinit var fraging: FragmentSelectedBinding
    lateinit var homeAdapter: HomeAdapter
    lateinit var requestQueue: RequestQueue

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fraging = FragmentSelectedBinding.inflate(inflater, container, false)
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
                                val sum = text.text.toString()
                                var its = money.Rate
                                if (sum.isNotEmpty()) {
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
                homeAdapter.notifyDataSetChanged()
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
                                nameFragment.visibility = View.GONE
                            }
                            userList.filter {
                                !it.isliked
                            }
                            homeAdapter.submitList(userList)
                        }
                    ) { error ->
                        Log.d(ContentValues.TAG,
                            "onErrorResponse: ${error?.message}")
                    }
                requestQueue.add(jsonArrayRequest)
            } else {
                Toast.makeText(requireContext(), "Isn`t connected", Toast.LENGTH_SHORT).show()
            }
        }
        return fraging.root
    }
}